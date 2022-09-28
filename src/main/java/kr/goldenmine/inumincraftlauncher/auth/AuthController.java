package kr.goldenmine.inumincraftlauncher.auth;

import kr.goldenmine.inumincraftlauncher.request.RetrofitServices;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import retrofit2.Response;
import retrofit2.http.GET;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

//@EnableWebMvc
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    public static CompletableFuture<String> future = new CompletableFuture<>();
    public static CompletableFuture<String> future2 = new CompletableFuture<>();
    public static String HTML = "";

    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET
    )
    public String test() {
        return "test";
    }

    @RequestMapping(
            value = "/authorization",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> authorization(
            final HttpServletRequest req,
            final HttpServletResponse res,
            @RequestParam Map<String,String> allRequestParams) throws Exception {

        String code = allRequestParams.get("code");
        String state = allRequestParams.get("state");
        String error = allRequestParams.get("error");
        String errorDescription = allRequestParams.get("error_description");

        System.out.println(code);
        System.out.println(state);
        System.out.println(error);
        System.out.println(errorDescription);

        return ResponseEntity.ok("");
    }

    @RequestMapping(
            value = "/microsoft",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> authMicrosoft(@RequestParam Map<String,String> allRequestParams) throws Exception {
        String code = allRequestParams.get("code");
        String state = allRequestParams.get("state");
        String idToken = allRequestParams.get("id_token");
        String error = allRequestParams.get("error");
        String errorDescription = allRequestParams.get("error_description");

        System.out.println(allRequestParams);

        if(error == null) {
            log.info("code: " + code);
            log.info("state: " + state);
            log.info("token: " + idToken);

            future.complete(code);

            return ResponseEntity.ok("authorization succeed.");
        } else {
            log.warn(error);
            log.warn(errorDescription);

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error + "\n" + errorDescription);
        }
    }


//    @RequestMapping(
//            value = "/microsoftlogin",
//            method = RequestMethod.GET
//    )
//    public String login() throws IOException {
//        Response<ResponseBody> response = RetrofitServices.MICROSOFT_SERVICE.requestHTML(
//                "common",
//                "bdbbf15c-d072-4d57-be2c-c0702ad18be4",
//                "code",
//                "http://localhost:20200/auth/microsoft",
//                "query",
//                "XboxLive.signin offline_access",
//                "12345"
//        ).execute();
//
//        String html = response.body().string();
//
//        return html;
//    }
}
