package kr.goldenmine.inumincraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inumincraftlauncher.auth.MicrosoftService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Main {
    /*
    microsoft oauth
    https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow

    minecraft login
    https://mojang-api-docs.netlify.app/authentication/msa.html

    이제 마인크래프트 실행만 할 수 있으면 되는데...
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        WebDriverManager.chromedriver().setup();
//        SpringApplication.run(INUMinecraftLauncherMain.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        MicrosoftService.tryAllLogin();
    }
}