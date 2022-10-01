package kr.goldenmine.inuminecraftlauncher.login;

import kr.goldenmine.inuminecraftlauncher.request.MicrosoftServiceImpl;
import kr.goldenmine.inuminecraftlauncher.request.models.MicrosoftTokenResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.xbox.XBoxXstsResponse;
import kr.goldenmine.inuminecraftlauncher.util.LoopUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class AccountAutoLoginScheduler extends Thread {

    private final MicrosoftAccountService microsoftAccountService;

    private final MicrosoftKeyService microsoftKeyService;

    private final long sleepInMS;
    private boolean stop = false;

    @Autowired
    public AccountAutoLoginScheduler(MicrosoftAccountService microsoftAccountService, MicrosoftKeyService microsoftKeyService) {
        this.microsoftAccountService = microsoftAccountService;
        this.microsoftKeyService = microsoftKeyService;

        sleepInMS = 900 * 1000L; // 토큰 expire의 1/4

        if(!isAlive()) {
            start();
            log.info("scheduler started");
        }
    }

    public void stopSafely() {
        stop = true;
        interrupt();
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                long start = System.currentTimeMillis();
                tryAllLogin();

                long time = (System.currentTimeMillis() - start);

                log.info(time + " ms used for refreshing all accounts.");

                Thread.sleep(Math.max(sleepInMS - time, 1));
            } catch(InterruptedException ex) {
                log.warn(ex.getMessage());
            }
        }
    }

    private synchronized void tryAllLogin() {
        List<MicrosoftAccount> list = microsoftAccountService.list();
        MicrosoftKey primary = microsoftKeyService.getPrimary();

        final long CURRENT_TIME = System.currentTimeMillis();

        log.info("client id: " + primary.getClientId());
        log.info("client secret: " + primary.getClientSecret());
        log.info("current time: " + CURRENT_TIME);

        MicrosoftServiceImpl.clientId = primary.getClientId();
        MicrosoftServiceImpl.clientSecret = primary.getClientSecret();
        MicrosoftServiceImpl.state = UUID.randomUUID().toString(); // random state for security

        log.info("account size: " + list.size());

        for(MicrosoftAccount microsoftAccount : list) {
            try {
                log.info("trying to login " + microsoftAccount.getEmail() + ", " + (microsoftAccount.getTokenExpire() - CURRENT_TIME) / 1000 + "s remaining...");
                MicrosoftTokenResponse microsoftTokenResponse;
                // 1664643097505
                // 1664638641095

                if(microsoftAccount.getServerJoined() == 0 && CURRENT_TIME + sleepInMS * 1.5 >= microsoftAccount.getTokenExpire()) {
                    final long INNER_TIME = System.currentTimeMillis();
                    if(microsoftAccount.getRecentRefreshToken() == null) {
                        microsoftTokenResponse = LoopUtil.waitWhile(() -> {
                            try {
                                MicrosoftTokenResponse result = MicrosoftServiceImpl.firstLogin(microsoftAccount.getEmail(), microsoftAccount.getPassword());
                                if(result != null) {
                                    return Optional.of(result);
                                }
                            } catch (Exception ex) {
                                log.warn(ex.getMessage());
                            }
                            return Optional.empty();
                        }, 1000L, 5).get();
                    } else {
                        microsoftTokenResponse = LoopUtil.waitWhile(() -> {
                            try {
                                return Optional.of(MicrosoftServiceImpl.refresh(microsoftAccount.getRecentRefreshToken()));
                            } catch (Exception ex) {
                                log.warn(ex.getMessage());
                                return Optional.empty();
                            }
                        }, 1000L, 5).get();
                        log.info("refreshed " + microsoftAccount.getEmail());
                    }

                    XBoxXstsResponse xBoxResponse = LoopUtil.waitWhile(() -> {
                        try {
                            return Optional.of(MicrosoftServiceImpl.loginXbox(microsoftTokenResponse.getAccessToken()));
                        } catch (IOException e) {
                            log.warn(e.getMessage());
                            return Optional.empty();
                        }
                    }, 1000L, 5).get();

                    MinecraftProfileResponse minecraftProfileResponse = MicrosoftServiceImpl.getMinecraftProfile(xBoxResponse.getToken(), xBoxResponse.getPreviousUhs());

                    microsoftAccount.setTokenExpire(INNER_TIME + microsoftTokenResponse.getExpiresIn() * 1000L);
                    microsoftAccount.setRecentCode(microsoftTokenResponse.getCode());
                    microsoftAccount.setRecentAccessToken(microsoftTokenResponse.getAccessToken());
                    microsoftAccount.setRecentRefreshToken(microsoftTokenResponse.getRefreshToken());
                    microsoftAccount.setRecentProfileToken(xBoxResponse.getToken());
                    microsoftAccount.setMinecraftUsername(minecraftProfileResponse.getName());
                    microsoftAccount.setMinecraftUuid(minecraftProfileResponse.getId().toString());

                    microsoftAccountService.save(microsoftAccount);
                    log.info("logged " + microsoftAccount.getEmail() + ", " + microsoftAccount.getRecentProfileToken() + ", " + microsoftTokenResponse.getExpiresIn());
                } else {
                    log.info("skipped " + microsoftAccount.getEmail());
                }

                Thread.sleep(sleepInMS / 1000L);
            } catch (InterruptedException | IOException e) {
                log.error(e.getMessage());
            }
        }
        microsoftAccountService.flush();
    }
}
