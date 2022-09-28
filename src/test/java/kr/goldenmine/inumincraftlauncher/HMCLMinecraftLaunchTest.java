package kr.goldenmine.inumincraftlauncher;

import org.jackhuang.hmcl.game.HMCLGameRepository;
import org.jackhuang.hmcl.setting.Profile;

import java.io.File;

public class HMCLMinecraftLaunchTest {
    public static void main(String[] args) {
        File initial = new File(".minecraft");

        Profile profile = new Profile("inu", initial);

        HMCLGameRepository gameRepository = new HMCLGameRepository(profile, new File(".minecraft"));
        gameRepository.getVersions().forEach(it -> {
            System.out.println(it.getId() + ", " + it.getJavaVersion());
        });
    }
}
