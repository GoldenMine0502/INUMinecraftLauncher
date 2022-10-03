package kr.goldenmine.inuminecraftlauncher.login;

import kr.goldenmine.inuminecraftlauncher.models.ServerStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController("/account")
public class AccountController {
    private final MicrosoftAccountService microsoftAccountService;
    private final MicrosoftKeyService microsoftKeyService;

//    private HashMap<String, Long> ipJoined = new HashMap<>();

    public AccountController(MicrosoftAccountService microsoftAccountService, MicrosoftKeyService microsoftKeyService) {
        this.microsoftAccountService = microsoftAccountService;
        this.microsoftKeyService = microsoftKeyService;
    }

    @RequestMapping(
            value = "/random",
            method = RequestMethod.POST
    )
    public ResponseEntity<String> randomAccount(
            final HttpServletRequest req,
            final HttpServletResponse res) throws Exception {
        Optional<MicrosoftAccount> accountOptional = microsoftAccountService.selectOneAccount();

        if(accountOptional.isPresent()) {
            String remoteIp = req.getRemoteAddr();
            MicrosoftAccount account = accountOptional.get();
            account.setServerBorrowed(1);
            account.setServerBorrowedExpire(System.currentTimeMillis() + 300 * 1000L);
            account.setServerQuitted(0);
            account.setRecentAccessedIp(remoteIp);

            microsoftAccountService.save(account);

            log.info("borrowed " + account.getMinecraftUsername() + " to " + remoteIp);

            return ResponseEntity.ok(account.getRecentProfileToken());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @RequestMapping(
            value = "/key",
            method = RequestMethod.GET
    )
    public ResponseEntity<MicrosoftKey> getClientKey(
            final HttpServletRequest req,
            final HttpServletResponse res) {
        MicrosoftKey key = microsoftKeyService.getPrimary();

        if(key != null) {
             return ResponseEntity.ok(key);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @RequestMapping(
            value = "/status",
            method = RequestMethod.GET
    )
    public ResponseEntity<ServerStatusResponse> status(
            final HttpServletRequest req,
            final HttpServletResponse res
    ) {
        int available = microsoftAccountService.countAvailableAccounts();
        int total = microsoftAccountService.countAllAccounts();

        ServerStatusResponse response = new ServerStatusResponse(available, total);

        return ResponseEntity.ok(response);
    }
}
