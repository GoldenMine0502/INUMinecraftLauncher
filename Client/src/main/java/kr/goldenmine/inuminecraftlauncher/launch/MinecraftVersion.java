package kr.goldenmine.inuminecraftlauncher.launch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class MinecraftVersion {
    private String id;
    private String type;
    private String url;
    private String time;
    private String releaseTime;
}
