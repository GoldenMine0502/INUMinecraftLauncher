package kr.goldenmine.inumincraftlauncher.request;

import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxXstsRequest;
import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxXstsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface XBoxXstsService {
    /*
        https://user.auth.xboxlive.com/user/authenticate
     */
    @POST("xsts/authorize")
    Call<XBoxXstsResponse> authenticate(@Body XBoxXstsRequest request);

}
