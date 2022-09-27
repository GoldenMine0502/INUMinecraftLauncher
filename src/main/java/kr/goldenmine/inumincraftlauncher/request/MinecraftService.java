package kr.goldenmine.inumincraftlauncher.request;

import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftLoginRequest;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftLoginResponse;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftProfileRequest;
import kr.goldenmine.inumincraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxXstsRequest;
import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxXstsResponse;
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
