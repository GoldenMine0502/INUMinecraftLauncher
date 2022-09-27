package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxLiveRequestProperties {
    /*
        "AuthMethod": "RPS",
    "SiteName": "user.auth.xboxlive.com",
    "RpsTicket": "<accesstoken>"
     */

    @SerializedName("AuthMethod")
    private String authMethod;

    @SerializedName("SiteName")
    private String siteName;

    @SerializedName("RpsTicket")
    private String accessToken;
}
