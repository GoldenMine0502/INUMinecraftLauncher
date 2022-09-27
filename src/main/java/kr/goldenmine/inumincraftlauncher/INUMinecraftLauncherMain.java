package kr.goldenmine.inumincraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inumincraftlauncher.request.RetrofitServices;
import kr.goldenmine.inumincraftlauncher.request.models.*;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftLoginRequest;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftLoginResponse;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import kr.goldenmine.inumincraftlauncher.request.models.xbox.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class INUMinecraftLauncherMain {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow
        WebDriverManager.chromedriver().setup();
        SpringApplication.run(INUMinecraftLauncherMain.class, args);

        String id = null;
        String password = null;
        String clientId = null;
        String clientSecret = null;

        try(BufferedReader reader = new BufferedReader(new FileReader("account/account.txt"))) {
            id = reader.readLine();
            password = reader.readLine();
            clientId = reader.readLine();
            clientSecret = reader.readLine();
        }

        String scope = "XboxLive.signin offline_access";

        String url = RetrofitServices.MICROSOFT_SERVICE.requestAuthorizationCode(
                "consumers",
                clientId,
                "code",
                "http://localhost:20200/auth/microsoft",
                "query",
                scope,
                "12345"
        ).request().url().url().toString();
        System.out.println(url);

        ChromeDriver driver = new ChromeDriver();
        // load login html
        driver.get(url);

        Optional<WebElement> optionalIdElement = driver.findElements(By.tagName("input")).stream().filter(it -> "email".equals(it.getAttribute("type"))).findFirst();
        Optional<WebElement> optionalSubmitElement = driver.findElements(By.tagName("input")).stream().filter(it -> "submit".equals(it.getAttribute("type"))).findFirst();

        if(optionalIdElement.isEmpty() || optionalSubmitElement.isEmpty()) {
            return;
        }

        WebElement idElement = optionalIdElement.get();
        WebElement submitElement = optionalSubmitElement.get();

        idElement.sendKeys(id);
        submitElement.click();

        // wait password element is displayed
        while(true) {
            try {
                Optional<WebElement> optionalPasswordElement = driver.findElements(By.tagName("input")).stream().filter(it -> "password".equals(it.getAttribute("type"))).findFirst();

                if(optionalPasswordElement.isPresent() && optionalPasswordElement.get().isDisplayed()) {
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Thread.sleep(1000L);
        }

        WebElement passwordElement = driver.findElements(By.tagName("input")).stream().filter(it -> "password".equals(it.getAttribute("type"))).findFirst().get();
        submitElement = driver.findElements(By.tagName("input")).stream().filter(it -> "submit".equals(it.getAttribute("type"))).findFirst().get();

        passwordElement.sendKeys(password);
        submitElement.click();

        while(true) {
            try {
                Optional<WebElement> optionalNextButton = driver.findElements(By.tagName("input")).stream().filter(it -> "button".equals(it.getAttribute("type")) && "아니요".equals(it.getAttribute("value"))).findFirst();

                if(optionalNextButton.isPresent() && optionalNextButton.get().isDisplayed()) {
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Thread.sleep(1000L);
        }

        WebElement nextButton = driver.findElements(By.tagName("input")).stream().filter(it -> "button".equals(it.getAttribute("type")) && "아니요".equals(it.getAttribute("value"))).findFirst().get();
        nextButton.click();

        String code = AuthController.future.get();

        System.out.println("received code: " + code);

        // get access token
        Response<MicrosoftTokenResponse> tokenResponse = RetrofitServices.MICROSOFT_SERVICE.requestAccessToken(
                "consumers",
                clientId,
                scope,
                code,
                "http://localhost:20200/auth/microsoft",
                "authorization_code",
                clientSecret
        ).execute();

        if (tokenResponse.isSuccessful()) {
            String accessToken = tokenResponse.body().getAccessToken();
            String refreshToken = tokenResponse.body().getRefreshToken();

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

            // minecraft login
            MinecraftLoginRequest minecraftLoginRequest = new MinecraftLoginRequest("XBL3.0 x=" + uhs + ";" + xstsResponse.getToken());
            MinecraftLoginResponse minecraftLoginResponse = RetrofitServices.MINECRAFT_LOGIN_SERVICE.authenticate(minecraftLoginRequest).execute().body();

            MinecraftProfileResponse minecraftProfileResponse = RetrofitServices.MINECRAFT_LOGIN_SERVICE.getProfile(minecraftLoginResponse.getTokenType() + " " + minecraftLoginResponse.getAccessToken()).execute().body();

            System.out.println(minecraftProfileResponse.getId());
            System.out.println(minecraftProfileResponse.getName());
        }
    }

//            RetrofitServices.XBOX_LIVE_SERVICE.authenticate()
}


//        driver.get(url2);

//        driver.executeScript("document.innerHTML = " + html);
//        driver.executeScript("document.innerHTML = '" + StringEscapeUtils.escapeEcmaScript(html) + "'");
//        driver.get("data:text/html;charset=utf-8," + response.body().string());