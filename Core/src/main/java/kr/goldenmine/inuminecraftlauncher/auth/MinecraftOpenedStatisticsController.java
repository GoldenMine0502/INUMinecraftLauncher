package kr.goldenmine.inuminecraftlauncher.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/statistics/opened")
public class MinecraftOpenedStatisticsController {
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/join"
    )
    public ResponseEntity<?> joinedUsers(@RequestParam("joined_players") String[] joined) {
        return ResponseEntity.ok(null);
    }
}
