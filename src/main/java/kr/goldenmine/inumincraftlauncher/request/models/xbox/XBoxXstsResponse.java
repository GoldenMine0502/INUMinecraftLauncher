package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class XBoxXstsResponse extends MicrosoftErrorResponse {
    @SerializedName("IssueInstant")
    String issueInstant;

    @SerializedName("NotAfter")
    String notAfter;

    @SerializedName("Token")
    String token;

    @SerializedName("DisplayClaims")
    XBoxXstsResponseDisplayClaims displayClaims;
}