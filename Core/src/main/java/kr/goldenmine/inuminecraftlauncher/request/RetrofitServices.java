package kr.goldenmine.inuminecraftlauncher.request;

import com.google.gson.*;
import kr.goldenmine.inuminecraftlauncher.util.UUIDUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Type;
import java.util.UUID;

public class RetrofitServices {
    public static final MicrosoftService MICROSOFT_SERVICE = new Retrofit.Builder()
            .baseUrl("https://login.microsoftonline.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(MicrosoftService.class);

    public static final XBoxUserService XBOX_LIVE_SERVICE = new Retrofit.Builder()
            .baseUrl("https://user.auth.xboxlive.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(XBoxUserService.class);

    public static final XBoxXstsService XBOX_LIVE_XSTS_SERVICE = new Retrofit.Builder()
            .baseUrl("https://xsts.auth.xboxlive.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(XBoxXstsService.class);

    public static final MinecraftService MINECRAFT_LOGIN_SERVICE = new Retrofit.Builder()
            .baseUrl("https://api.minecraftservices.com/")
            .addConverterFactory(GsonConverterFactory.create(getGsonForUUID()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(MinecraftService.class);

    private static Gson getGsonForUUID() {
        return new GsonBuilder()
                .registerTypeAdapter(UUID.class, new MinecraftUUIDDeserializer())
                .create();
    }

    private static class MinecraftUUIDDeserializer implements JsonDeserializer<UUID> {

        @Override
        public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String uuidString = json.getAsString();

            return UUIDUtil.getUUIDFromMinecraftRequestString(uuidString);
        }

    }
}
