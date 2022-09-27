package kr.goldenmine.inumincraftlauncher.request.models.minecraft;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MinecraftProfileResponse extends MinecraftErrorResponse  {
    @SerializedName("id")
    UUID id;

    @SerializedName("name")
    String name;

    @SerializedName("skins")
    List<MinecraftProfileResponseSkin> skins;

    @SerializedName("capes")
    List<MinecraftProfileResponseCape> capes;
}