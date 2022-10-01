package kr.goldenmine.inuminecraftlauncher.request;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServerService {
    @POST("/account/key")
    Call<MicrosoftKey> getClientKey();
}
