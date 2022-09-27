package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxLiveRequest {
    /*
    {
  "RelyingParty": "http://auth.xboxlive.com",
  "TokenType": "JWT",
  "Properties": {
    "AuthMethod": "RPS",
    "SiteName": "user.auth.xboxlive.com",
    "RpsTicket": "<accesstoken>"
  }`
}
     */
    @SerializedName("RelyingParty")
    private String relyingParty;

    @SerializedName("TokenType")
    private String tokenType;

    @SerializedName("Properties")
    XBoxLiveRequestProperties xBoxLiveRequestProperties;
}
