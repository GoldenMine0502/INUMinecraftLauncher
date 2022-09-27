package kr.goldenmine.inumincraftlauncher.request.models.minecraft;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MinecraftProfileResponseSkin {
    public String id;
    public String state;
    public String url;
    public String variant; // CLASSIC, SLIM
    public String alias;
}