package kr.goldenmine.inumincraftlauncher.third_party_api;

import org.jackhuang.hmcl.setting.Profile;
import org.jackhuang.hmcl.setting.Profiles;
import org.jackhuang.hmcl.ui.versions.Versions;

public class HMCLApi {
    public static void main(String[] args) {
        Profile profile;
    }



    private void launch() {
        Versions.launch(Profiles.getSelectedProfile());
    }
}

/*
    public MicrosoftSession(String tokenType, String accessToken, long notAfter, String refreshToken, User user, GameProfile profile) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.notAfter = notAfter;
        this.refreshToken = refreshToken;
        this.user = user;
        this.profile = profile;

        if (accessToken != null) Logging.registerAccessToken(accessToken);
    }

    public static class User {
        private final String id;

        public User(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static class GameProfile {
        private final UUID id;
        private final String name;

        public GameProfile(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
 */