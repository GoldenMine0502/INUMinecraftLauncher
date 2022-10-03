package kr.goldenmine.inuminecraftlauncher.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitServices {
    public static final MicrosoftService SERVER_SERVICE = new Retrofit.Builder()
            .baseUrl("http://minecraft.goldenmine.kr:20200")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(MicrosoftService.class);
}
