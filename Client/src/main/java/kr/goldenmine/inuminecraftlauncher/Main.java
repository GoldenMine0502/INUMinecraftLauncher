package kr.goldenmine.inuminecraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inuminecraftlauncher.ui.MainFrame;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class Main {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoreMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        new MainFrame().setVisible(true);
    }
}
