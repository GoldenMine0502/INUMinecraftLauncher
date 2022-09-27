package kr.goldenmine.inumincraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;


public class MicrosoftErrorResponse {
    @SerializedName("XErr")
    long errorCode;

    @SerializedName("Message")
    String message;

    @SerializedName("Redirect")
    String redirectUrl;
}