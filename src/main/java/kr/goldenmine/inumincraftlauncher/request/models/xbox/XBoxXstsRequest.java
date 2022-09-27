package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxXstsRequest {
    /*
                    .json(mapOf(
                        pair("Properties",
                                mapOf(pair("SandboxId", "RETAIL"),
                                        pair("UserTokens", Collections.singletonList(xboxResponse.token)))),
                        pair("RelyingParty", "rp://api.minecraftservices.com/"), pair("TokenType", "JWT")))
     */

    @SerializedName("TokenType")
    String tokenType;

    @SerializedName("RelyingParty")
    String relyingParty;

    @SerializedName("Properties")
    XBoxXstsRequestProperties properties;
}
