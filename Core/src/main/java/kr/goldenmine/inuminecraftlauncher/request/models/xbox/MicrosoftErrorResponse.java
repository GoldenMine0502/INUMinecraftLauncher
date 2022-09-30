package kr.goldenmine.inuminecraftlauncher.request.models.xbox;

import com.google.gson.annotations.SerializedName;


public class MicrosoftErrorResponse {
    @SerializedName("XErr")
    long errorCode;

    @SerializedName("Message")
    String message;

    @SerializedName("Redirect")
    String redirectUrl;
}