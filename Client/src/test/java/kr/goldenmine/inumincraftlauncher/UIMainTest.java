package kr.goldenmine.inumincraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inuminecraftlauncher.CoreMain;
import kr.goldenmine.inuminecraftlauncher.ui.MainFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class UIMainTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
//        SpringApplication.run(INUMinecraftLauncherMain.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoreMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        new MainFrame().setVisible(true);
    }
}
