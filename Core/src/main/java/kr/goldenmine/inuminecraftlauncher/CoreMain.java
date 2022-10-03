package kr.goldenmine.inuminecraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoreMain {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoreMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}
