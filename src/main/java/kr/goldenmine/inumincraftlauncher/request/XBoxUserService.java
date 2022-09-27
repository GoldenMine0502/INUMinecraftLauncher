package kr.goldenmine.inumincraftlauncher.request;

import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxLiveRequest;
import kr.goldenmine.inumincraftlauncher.request.models.xbox.XBoxLiveResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface XBoxUserService {
    /*
        https://user.auth.xboxlive.com/user/authenticate
     */
    @POST("user/authenticate")
    Call<XBoxLiveResponse> authenticate(@Body XBoxLiveRequest request);

}
