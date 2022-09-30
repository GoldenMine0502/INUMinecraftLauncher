package kr.goldenmine.inuminecraftlauncher.request.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MicrosoftTokenResponse {
    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private String scope;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @Override
    public String toString() {
        return "TokenRequest{" +
                "tokenType='" + tokenType + '\'' +
                ", scope='" + scope + '\'' +
                ", expiresIn=" + expiresIn +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
