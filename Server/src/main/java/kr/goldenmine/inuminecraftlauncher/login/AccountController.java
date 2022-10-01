package kr.goldenmine.inuminecraftlauncher.login;

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

@RestController("/account")
public class AccountController {
    private final MicrosoftAccountService microsoftAccountService;
    private final MicrosoftKeyService microsoftKeyService;

    private HashMap<String, Long> ipJoined = new HashMap<>();

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
            MicrosoftAccount account = accountOptional.get();

            return ResponseEntity.ok(account.getRecentProfileToken());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @RequestMapping(
            value = "/key",
            method = RequestMethod.POST
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
}
