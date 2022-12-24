package kr.goldenmine.inuminecraftlauncher;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
@AllArgsConstructor
public class MinecraftOptions {
    private File javaRoute;
    private List<String> jvmArguments;
    private int versionId;
    private int minMemory;
    private int maxMemory;
    private String serverIp;
}
