package kr.goldenmine.inuminecraftlauncher.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/statistics")
public class StatisticsController {

    @Autowired
    private MicrosoftAccountService microsoftAccountService;

    @RequestMapping(
            value = "/join",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> join(String username) {
        List<MicrosoftAccount> accounts = microsoftAccountService.getAccountFromUsername(username);

        if (accounts.size() > 0) {
            MicrosoftAccount account = accounts.get(0);

            account.setServerJoined(1);
            account.setServerQuitted(0);

            microsoftAccountService.save(account);

            log.info("joined " + account.getMinecraftUsername() + " to minecraft server.");

            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
        }
    }

    @RequestMapping(
            value = "/quit",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> quit(String username) {
        List<MicrosoftAccount> accounts = microsoftAccountService.getAccountFromUsername(username);

        if (accounts.size() > 0) {
            MicrosoftAccount account = accounts.get(0);

            account.setServerQuitted(1);

            microsoftAccountService.save(account);

            log.info("quitted " + account.getMinecraftUsername() + " to minecraft server.");

            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
        }
    }
}