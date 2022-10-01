package kr.goldenmine.inuminecraftlauncher.request;

import kr.goldenmine.inuminecraftlauncher.auth.AuthController;
import kr.goldenmine.inuminecraftlauncher.request.RetrofitServices;
import kr.goldenmine.inuminecraftlauncher.request.models.MicrosoftTokenResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftLoginRequest;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftLoginResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.xbox.*;
import kr.goldenmine.inuminecraftlauncher.util.LoopUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

@Slf4j
public class MicrosoftServiceImpl {
    public static String clientId;
    public static String clientSecret;
    public static String state = UUID.randomUUID().toString();

    public static void loadFromFile(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            clientId = reader.readLine();
            clientSecret = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void tryAllLogin() {
        File file = new File("account/ids.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int count = Integer.parseInt(reader.readLine());

            for(int i = 0; i < count; i++) {
                String id = reader.readLine();
                String password = reader.readLine();

                AuthController.future = new CompletableFuture<>();

                MicrosoftTokenResponse token = firstLogin(id, password);
                System.out.println(token.getAccessToken());
                System.out.println(token.getRefreshToken());
                XBoxXstsResponse xstsResponse = loginXbox(token.getAccessToken());
                MinecraftProfileResponse profile = getMinecraftProfile(xstsResponse.getToken(), xstsResponse.getPreviousUhs());

                System.out.println(profile.getId());
                System.out.println(profile.getName());
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static synchronized MicrosoftTokenResponse refresh(String refreshToken) throws IOException {
        String scope = "XboxLive.signin offline_access";

        Response<MicrosoftTokenResponse> tokenResponse = RetrofitServices.MICROSOFT_SERVICE.requestAccessToken(
                "consumers",
                clientId,
                scope,
                "http://localhost:20200/auth/microsoft",
                "authorization_code",
                clientSecret,
                refreshToken
        ).execute();

        return tokenResponse.body();
    }

    public static synchronized MicrosoftTokenResponse firstLogin(String id, String password) throws InterruptedException, ExecutionException, IOException {
        AuthController.future = new CompletableFuture<>();
        String scope = "XboxLive.signin offline_access";

        String url = RetrofitServices.MICROSOFT_SERVICE.requestAuthorizationCode(
                "consumers",
                clientId,
                "code",
                "http://localhost:20200/auth/microsoft",
                "query",
                scope,
                state
        ).request().url().url().toString();

        ChromeDriver driver = new ChromeDriver();
        // load login html
        driver.get(url);
        Thread.sleep(1000L);

        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
        
        try {
            WebElement idElement = LoopUtil.waitWhile(() -> driver.findElements(By.tagName("input")).stream().filter(it -> "email".equals(it.getAttribute("type"))).findFirst(), 1000L, -1).get();

            Optional<WebElement> optionalSubmitElement = driver.findElements(By.tagName("input")).stream().filter(it -> "submit".equals(it.getAttribute("type"))).findFirst();
            WebElement submitElement = optionalSubmitElement.get();

            idElement.sendKeys(id);
            Thread.sleep(500L);
            submitElement.click();

            WebElement passwordElement = LoopUtil.waitWhile(() -> driver.findElements(By.tagName("input")).stream().filter(it -> "password".equals(it.getAttribute("type"))).findFirst(), 1000L, -1).get();
            submitElement = driver.findElements(By.tagName("input")).stream().filter(it -> "submit".equals(it.getAttribute("type"))).findFirst().get();

            passwordElement.sendKeys(password);
            Thread.sleep(500L);
            submitElement.click();

            Optional<WebElement> find = LoopUtil.waitWhile(() -> driver.findElements(By.tagName("input")).stream().filter(it -> "button".equals(it.getAttribute("type")) && "아니요".equals(it.getAttribute("value"))).findFirst(), 1000L, 10);

            if (find.isPresent()) {
                WebElement nextButton = driver.findElements(By.tagName("input")).stream().filter(it -> "button".equals(it.getAttribute("type")) && "아니요".equals(it.getAttribute("value"))).findFirst().get();
                nextButton.click();
            }

            String code = AuthController.future.get();

            Response<MicrosoftTokenResponse> tokenResponse = RetrofitServices.MICROSOFT_SERVICE.requestAccessToken(
                    "consumers",
                    clientId,
                    scope,
                    code,
                    "http://localhost:20200/auth/microsoft",
                    "authorization_code",
                    clientSecret
            ).execute();

            log.info(tokenResponse.body().toString());

            tokenResponse.body().setCode(code);

            return tokenResponse.body();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        } finally {
            driver.quit();
        }

        return null;
    }

    public static XBoxXstsResponse loginXbox(String accessToken) throws IOException {
        // xbox login
        XBoxLiveRequest request = new XBoxLiveRequest(
                "http://auth.xboxlive.com",
                "JWT",
                new XBoxLiveRequestProperties(
                        "RPS",
                        "user.auth.xboxlive.com",
                        "d=" + accessToken
                )
        );
        XBoxLiveResponse xboxResponse = RetrofitServices.XBOX_LIVE_SERVICE.authenticate(request).execute().body();
        String uhs = xboxResponse.getDisplayClaims().getUhs(null);

        // xsts login
        XBoxXstsRequest xstsRequest = new XBoxXstsRequest("JWT", "rp://api.minecraftservices.com/", new XBoxXstsRequestProperties(
                "RETAIL",
                Collections.singletonList(xboxResponse.getToken())
        ));
        XBoxXstsResponse xstsResponse = RetrofitServices.XBOX_LIVE_XSTS_SERVICE.authenticate(xstsRequest).execute().body();

        // check whether uhs is same
        xstsResponse.getDisplayClaims().getUhs(uhs);

        xstsResponse.setPreviousUhs(uhs);

        return xstsResponse;
    }

    public static MinecraftProfileResponse getMinecraftProfile(String token, String uhs) throws IOException {
        // minecraft login
        MinecraftLoginRequest minecraftLoginRequest = new MinecraftLoginRequest("XBL3.0 x=" + uhs + ";" + token);
        MinecraftLoginResponse minecraftLoginResponse = RetrofitServices.MINECRAFT_LOGIN_SERVICE.authenticate(minecraftLoginRequest).execute().body();

        MinecraftProfileResponse minecraftProfileResponse = RetrofitServices.MINECRAFT_LOGIN_SERVICE.getProfile(minecraftLoginResponse.getTokenType() + " " + minecraftLoginResponse.getAccessToken()).execute().body();

        return minecraftProfileResponse;
    }
}
