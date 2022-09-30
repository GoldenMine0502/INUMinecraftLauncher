package kr.goldenmine.inuminecraftlauncher.request;

import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftLoginRequest;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftLoginResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MinecraftService {
    /*
        https://user.auth.xboxlive.com/user/authenticate
     */
    @POST("authentication/login_with_xbox")
    Call<MinecraftLoginResponse> authenticate(@Body MinecraftLoginRequest request);

    @GET("minecraft/profile")
    Call<MinecraftProfileResponse> getProfile(@Header("authorization") String authorization);
}
