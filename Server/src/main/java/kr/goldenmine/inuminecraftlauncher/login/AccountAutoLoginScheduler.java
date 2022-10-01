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
                tryAllLogin();

                Thread.sleep(sleepInMS);
            } catch(InterruptedException ex) {
                log.warn(ex.getMessage());
            }
        }
    }

    private synchronized void tryAllLogin() {
        List<MicrosoftAccount> list = microsoftAccountService.list();
        MicrosoftKey primary = microsoftKeyService.getPrimary();

        log.info("client id: " + primary.getClientId());
        log.info("client secret: " + primary.getClientSecret());

        MicrosoftServiceImpl.clientId = primary.getClientId();
        MicrosoftServiceImpl.clientSecret = primary.getClientSecret();
        MicrosoftServiceImpl.state = UUID.randomUUID().toString(); // random state for security

        log.info("login count: " + list.size());

        for(MicrosoftAccount microsoftAccount : list) {
            try {
                log.info("try to login " + microsoftAccount.getEmail());
                MicrosoftTokenResponse microsoftTokenResponse = LoopUtil.waitWhile(() -> {
                    try {
                        return Optional.of(MicrosoftServiceImpl.firstLogin(microsoftAccount.getEmail(), microsoftAccount.getPassword()));
                    } catch (Exception ex) {
                        log.warn(ex.getMessage());
                        return Optional.empty();
                    }
                }, 1000L, 5).get();

                XBoxXstsResponse xBoxResponse = LoopUtil.waitWhile(() -> {
                    try {
                        return Optional.of(MicrosoftServiceImpl.loginXbox(microsoftTokenResponse.getAccessToken()));
                    } catch (IOException e) {
                        log.warn(e.getMessage());
                        return Optional.empty();
                    }
                }, 1000L, 5).get();

                MinecraftProfileResponse minecraftProfileResponse = MicrosoftServiceImpl.getMinecraftProfile(xBoxResponse.getToken(), xBoxResponse.getPreviousUhs());

                microsoftAccount.setTokenExpire(System.currentTimeMillis() + microsoftTokenResponse.getExpiresIn() * 1000L);
                microsoftAccount.setRecentAccessToken(microsoftTokenResponse.getAccessToken());
                microsoftAccount.setRecentRefreshToken(microsoftTokenResponse.getRefreshToken());
                microsoftAccount.setRecentProfileToken(xBoxResponse.getToken());
                microsoftAccount.setMinecraftUsername(minecraftProfileResponse.getName());
                microsoftAccount.setMinecraftUUID(minecraftProfileResponse.getId().toString());

                microsoftAccountService.save(microsoftAccount);
                log.info("logged " + microsoftAccount.getEmail() + ", " + microsoftAccount.getRecentProfileToken());
            } catch (InterruptedException | IOException e) {
                log.error(e.getMessage());
            }
        }
        microsoftAccountService.flush();
    }
}
