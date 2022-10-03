package kr.goldenmine.inuminecraftlauncher.util;

import java.util.UUID;

public class UUIDUtil {
    public static UUID getUUIDFromMinecraftRequestString(String uuidString) {
        // 0   ~  8,9~12,13~16,17~20,20~32
        // af92e82a-610a-45e3-a443-ce07e9dafe4e
        // 0f14d0ab-9605-4a62-a9e4-5ed26688389b

        String convertedString = uuidString.substring(0, 8) + "-"
                + uuidString.substring(9, 12) + "-"
                + uuidString.substring(13, 16) + "-"
                + uuidString.substring(17, 20) + "-"
                + uuidString.substring(20);

        return UUID.fromString(convertedString);
    }
}
