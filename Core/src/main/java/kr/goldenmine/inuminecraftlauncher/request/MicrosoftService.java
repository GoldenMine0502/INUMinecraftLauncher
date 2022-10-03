package kr.goldenmine.inuminecraftlauncher.request;

import kr.goldenmine.inuminecraftlauncher.request.models.MicrosoftTokenResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface MicrosoftService {
    // scope:
    // client id: bdbbf15c-d072-4d57-be2c-c0702ad18be4
    // client secret value: fOB8Q~rkevnm03wuXVUm2lwzHhNs1bJc5r-eMad-
    // client secret id: 1dc9653e-0cdb-4ad9-bac8-b5a191b64b28

    //    @FormUrlEncoded
    @GET("{tenant}/oauth2/v2.0/authorize")
    Call<ResponseBody> requestAuthorizationCode(
            @Path("tenant") String tenant,
            @Query("client_id") String clientId,
            @Query("response_type") String responseType,
            @Query("redirect_uri") String redirectURI,
            @Query("response_mode") String responseMode,
            @Query("scope") String scope,
            @Query("state") String state
//            @Field("code_challenge") String codeChallenge,
//            @Field("code_challenge_method") String codeChallengeMethod
    );



    @FormUrlEncoded
    @POST("{tenant}/oauth2/v2.0/token")
    Call<MicrosoftTokenResponse> requestAccessToken(
            @Path("tenant") String tenant,
            @Field("client_id") String clientId,
            @Field("scope") String scope,
            @Field("code") String code,
//            @Query("response_type") String responseType,
            @Field("redirect_uri") String redirectURI,
            @Field("grant_type") String grantType,
            @Field("client_secret") String clientSecret
//            @Field("code_challenge") String codeChallenge,
//            @Field("code_challenge_method") String codeChallengeMethod
    );

    @FormUrlEncoded
    @POST("{tenant}/oauth2/v2.0/token")
    Call<MicrosoftTokenResponse> requestRefreshToken(
            @Path("tenant") String tenant,
            @Field("client_id") String clientId,
            @Field("scope") String scope,
//            @Query("response_type") String responseType,
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType,
            @Field("client_secret") String clientSecret
//            @Field("code_challenge") String codeChallenge,
//            @Field("code_challenge_method") String codeChallengeMethod
    );
}
