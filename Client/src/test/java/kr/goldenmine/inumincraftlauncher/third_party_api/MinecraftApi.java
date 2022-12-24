package kr.goldenmine.inumincraftlauncher.third_party_api;

import com.jayway.jsonpath.internal.function.numeric.Min;
import kr.goldenmine.inuminecraftlauncher.auth.MicrosoftSession;
import kr.goldenmine.inuminecraftlauncher.launch.MinecraftVersion;
import sk.tomsik68.mclauncher.api.login.ISession;
import sk.tomsik68.mclauncher.backend.GlobalAuthenticationSystem;
import sk.tomsik68.mclauncher.backend.MinecraftLauncherBackend;
import sk.tomsik68.mclauncher.impl.common.Platform;

import java.io.File;
import java.util.Arrays;

public class MinecraftApi {
    public static void main(String[] args) throws Exception {
        File workingDirectory = Platform.getCurrentPlatform().getWorkingDirectory();

        System.out.println(workingDirectory.getAbsolutePath());

//        String[] authProfileNames = GlobalAuthenticationSystem.getProfileNames();
//
//        System.out.println(Arrays.toString(authProfileNames));
//
//        int selection = 0;
//
//
//        MinecraftVersion mc1165 = new MinecraftVersion(
//                "1.16.5",
//                "release",
//                "https://launchermeta.mojang.com/v1/packages/95af6e50cd04f06f65c76e4a62237504387e5480/1.16.5.json",
//                "2022-02-25T13:15:31+00:00",
//                "2021-01-14T16:05:32+00:00"
//        );

//        MicrosoftSession session = new MicrosoftSession();



//        ISession loginSession = GlobalAuthenticationSystem.login(authProfileNames[selection]);
//        MinecraftLauncherBackend launcher = new MinecraftLauncherBackend(workingDirectory);
//
//        launcher.launchMinecraft(loginSession, "1.12.2");

        /*
            {
      "id": "1.16.5",
      "type": "release",
      "url": "https://launchermeta.mojang.com/v1/packages/95af6e50cd04f06f65c76e4a62237504387e5480/1.16.5.json",
      "time": "2022-02-25T13:15:31+00:00",
      "releaseTime": "2021-01-14T16:05:32+00:00"
    },
         */
    }
}
