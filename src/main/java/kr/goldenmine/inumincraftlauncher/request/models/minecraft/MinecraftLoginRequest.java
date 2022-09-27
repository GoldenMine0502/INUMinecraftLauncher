package kr.goldenmine.inumincraftlauncher.request.models.minecraft;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MinecraftLoginRequest {
    /*
                    .json(mapOf(pair("identityToken", "XBL3.0 x=" + uhs + ";" + minecraftXstsResponse.token)))
     */

    @SerializedName("identityToken")
    String identityToken;
}
