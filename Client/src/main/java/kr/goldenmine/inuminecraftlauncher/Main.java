package kr.goldenmine.inuminecraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inuminecraftlauncher.ui.MainFrame;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;

public class Main {

    // 마인크래프트 런쳐를 위한 api
    // https://github.com/tomsik68/mclauncher-api

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoreMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        new MainFrame().setVisible(true);

//        MinecraftOptions options = new MinecraftOptions(new File("java/jdk-8u202/bin/java"), new ArrayList<>(), 36);
    }
}
