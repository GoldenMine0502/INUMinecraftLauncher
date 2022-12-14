package kr.goldenmine.inumincraftlauncher;

import io.github.bonigarcia.wdm.WebDriverManager;
import kr.goldenmine.inuminecraftlauncher.CoreMain;
import kr.goldenmine.inuminecraftlauncher.request.MicrosoftServiceImpl;
import kr.goldenmine.inuminecraftlauncher.ui.MainFrame;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class UIMainTest {
    public static void main(String[] args) {
        MicrosoftServiceImpl.clientId = "28cef4b5-c752-4c4b-a08e-b9cfaf887080";
        MicrosoftServiceImpl.clientSecret = "PXg8Q~Ki2Vzk8qpBW~MtJSfMABXBTMBOSL9GGcXV";

        WebDriverManager.chromedriver().setup();

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoreMain.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        new MainFrame().setVisible(true);
    }
}
