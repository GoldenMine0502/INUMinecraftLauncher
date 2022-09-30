package kr.goldenmine.inuminecraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxXstsRequestProperties {
    /*
                            pair("Properties",
                                mapOf(pair("SandboxId", "RETAIL"),
                                        pair("UserTokens", Collections.singletonList(xboxResponse.token)))),
     */

    @SerializedName("SandboxId")
    String sandboxId;

    @SerializedName("UserTokens")
    List<String> userTokens;
}
