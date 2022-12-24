package kr.goldenmine.inuminecraftlauncher;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class MinecraftLauncher {
    private File root;

    public MinecraftLauncher(File root) {
        this.root = root;
    }

    public void runMinecraft() {

    }

//    private CommandBuilder generateCommandLine(MinecraftOptions options) throws IOException {
//        CommandBuilder res = new CommandBuilder();
//
//        // Executable
////        if (StringUtils.isNotBlank(options.getWrapper()))
////            res.addAllWithoutParsing(StringUtils.tokenize(options.getWrapper()));
//
//        res.add(options.getJavaRoute().toString());
//
//        res.addAllWithoutParsing(options.getJvmArguments());
//
//        Charset encoding = StandardCharsets.UTF_8;
//
//        res.addDefault("-Dfile.encoding=", "UTF-8");
//        try {
//            String stdoutEncoding = res.addDefault("-Dsun.stdout.encoding=", encoding.name());
//            if (stdoutEncoding != null)
//                encoding = Charset.forName(stdoutEncoding.substring("-Dsun.stdout.encoding=".length()));
//        } catch (Throwable ex) {
//            encoding = OperatingSystem.NATIVE_CHARSET;
//            log.warn("Bad stdout encoding", ex);
//        }
//
//        res.addDefault("-Dsun.stderr.encoding=", encoding.name());
//
//        appendJvmArgs(res);
//
////        res.addDefault("-Dminecraft.client.jar=", repository.getVersionJar(version).toString());
//
//        if (OperatingSystem.CURRENT_OS == OperatingSystem.OSX) {
//            res.addDefault("-Xdock:name=", "Minecraft " + options.getVersionId());
////            repository.getAssetObject(version.getId(), version.getAssetIndex().getId(), "icons/minecraft.icns")
////                    .ifPresent(minecraftIcns -> {
////                        res.addDefault("-Xdock:icon=", minecraftIcns.toAbsolutePath().toString());
////                    });
//        }
//
////        if (OperatingSystem.CURRENT_OS != OperatingSystem.WINDOWS)
////            res.addDefault("-Duser.home=", options.getGameDir().getParent());
//
//        boolean addG1Args = true;
//        for (String javaArg : options.getJvmArguments()) {
//            if ("-XX:-UseG1GC".equals(javaArg) || (javaArg.startsWith("-XX:+Use") && javaArg.endsWith("GC"))) {
//                addG1Args = false;
//                break;
//            }
//        }
//        if (addG1Args) {
//            res.addUnstableDefault("UnlockExperimentalVMOptions", true);
//            res.addUnstableDefault("UseG1GC", true);
//            res.addUnstableDefault("G1NewSizePercent", "20");
//            res.addUnstableDefault("G1ReservePercent", "20");
//            res.addUnstableDefault("MaxGCPauseMillis", "50");
//            res.addUnstableDefault("G1HeapRegionSize", "16m");
//        }
//
////            if (options.getMetaspace() != null && options.getMetaspace() > 0)
////                    res.addDefault("-XX:MetaspaceSize=", options.getMetaspace() + "m");
//
//        res.addUnstableDefault("UseAdaptiveSizePolicy", false);
//        res.addUnstableDefault("OmitStackTraceInFastThrow", false);
//        res.addUnstableDefault("DontCompileHugeMethods", false);
//        res.addDefault("-Xmn", "128m");
//
//        // As 32-bit JVM allocate 320KB for stack by default rather than 64-bit version allocating 1MB,
//        // causing Minecraft 1.13 crashed accounting for java.lang.StackOverflowError.
////            if (options.getJava().getBits() == Bits.BIT_32) {
////                res.addDefault("-Xss", "1m");
////            }
//
//        if (options.getMaxMemory() > 0)
//            res.addDefault("-Xmx", options.getMaxMemory() + "m");
//
//        if (options.getMinMemory() > 0)
//            res.addDefault("-Xms", options.getMinMemory() + "m");
//
////        if (options.getJava().getParsedVersion() == JavaVersion.JAVA_16)
////            res.addDefault("--illegal-access=", "permit");
//
//        res.addDefault("-Dfml.ignoreInvalidMinecraftCertificates=", "true");
//        res.addDefault("-Dfml.ignorePatchDiscrepancies=", "true");
//
//        // Fix RCE vulnerability of log4j2
//        res.addDefault("-Djava.rmi.server.useCodebaseOnly=", "true");
//        res.addDefault("-Dcom.sun.jndi.rmi.object.trustURLCodebase=", "false");
//        res.addDefault("-Dcom.sun.jndi.cosnaming.object.trustURLCodebase=", "false");
//
//        String formatMsgNoLookups = res.addDefault("-Dlog4j2.formatMsgNoLookups=", "true");
////        if (!"-Dlog4j2.formatMsgNoLookups=false".equals(formatMsgNoLookups) && isUsingLog4j()) {
////            res.addDefault("-Dlog4j.configurationFile=", getLog4jConfigurationFile().getAbsolutePath());
////        }
//
////        Proxy proxy = options.getProxy();
////        if (proxy != null && StringUtils.isBlank(options.getProxyUser()) && StringUtils.isBlank(options.getProxyPass())) {
////            InetSocketAddress address = (InetSocketAddress) options.getProxy().address();
////            if (address != null) {
////                String host = address.getHostString();
////                int port = address.getPort();
////                if (proxy.type() == Proxy.Type.HTTP) {
////                    res.addDefault("-Dhttp.proxyHost=", host);
////                    res.addDefault("-Dhttp.proxyPort=", String.valueOf(port));
////                    res.addDefault("-Dhttps.proxyHost=", host);
////                    res.addDefault("-Dhttps.proxyPort=", String.valueOf(port));
////                } else if (proxy.type() == Proxy.Type.SOCKS) {
////                    res.addDefault("-DsocksProxyHost=", host);
////                    res.addDefault("-DsocksProxyPort=", String.valueOf(port));
////                }
////            }
////        }
//
////        List<String> classpath = repository.getClasspath(version);
////
////        File jar = repository.getVersionJar(version);
////        if (!jar.exists() || !jar.isFile())
////            throw new IOException("Minecraft jar does not exist");
////        classpath.add(jar.getAbsolutePath());
//
//        // Provided Minecraft arguments
////        Path gameAssets = repository.getActualAssetDirectory(version.getId(), version.getAssetIndex().getId());
////        Map<String, String> configuration = getConfigurations();
////        configuration.put("${classpath}", String.join(OperatingSystem.PATH_SEPARATOR, classpath));
////        configuration.put("${game_assets}", gameAssets.toAbsolutePath().toString());
////        configuration.put("${assets_root}", gameAssets.toAbsolutePath().toString());
//
//        // lwjgl assumes path to native libraries encoded by ASCII.
//        // Here is a workaround for this issue: https://github.com/huanghongxun/HMCL/issues/1141.
////        String nativeFolderPath = nativeFolder.getAbsolutePath();
////        Path tempNativeFolder = null;
////        if ((OperatingSystem.CURRENT_OS == OperatingSystem.LINUX || OperatingSystem.CURRENT_OS == OperatingSystem.OSX)
////                && !StringUtils.isASCII(nativeFolderPath)) {
////            tempNativeFolder = Paths.get("/", "tmp", "hmcl-natives-" + UUID.randomUUID());
////            nativeFolderPath = tempNativeFolder + File.pathSeparator + nativeFolderPath;
////        }
////        configuration.put("${natives_directory}", nativeFolderPath);
//
////        res.addAll(Arguments.parseArguments(version.getArguments().map(Arguments::getJvm).orElseGet(this::getDefaultJVMArguments), configuration));
////        Arguments argumentsFromAuthInfo = authInfo.getLaunchArguments(options);
////        if (argumentsFromAuthInfo != null && argumentsFromAuthInfo.getJvm() != null && !argumentsFromAuthInfo.getJvm().isEmpty())
////            res.addAll(Arguments.parseArguments(argumentsFromAuthInfo.getJvm(), configuration));
//
////        for (String javaAgent : options.getJavaAgents()) {
////            res.add("-javaagent:" + javaAgent);
////        }
//
////        res.add(version.getMainClass());
//
////        res.addAll(Arguments.parseStringArguments(version.getMinecraftArguments().map(StringUtils::tokenize).orElseGet(ArrayList::new), configuration));
//
////        Map<String, Boolean> features = getFeatures();
////        version.getArguments().map(Arguments::getGame).ifPresent(arguments -> res.addAll(Arguments.parseArguments(arguments, configuration, features)));
////        if (version.getMinecraftArguments().isPresent()) {
////            res.addAll(Arguments.parseArguments(this.getDefaultGameArguments(), configuration, features));
////        }
////        if (argumentsFromAuthInfo != null && argumentsFromAuthInfo.getGame() != null && !argumentsFromAuthInfo.getGame().isEmpty())
////            res.addAll(Arguments.parseArguments(argumentsFromAuthInfo.getGame(), configuration, features));
//
//        if (StringUtils.isNotBlank(options.getServerIp())) {
//            String[] args = options.getServerIp().split(":");
//            res.add("--server");
//            res.add(args[0]);
//            res.add("--port");
//            res.add(args.length > 1 ? args[1] : "25565");
//        }
//
////        if (options.isFullscreen())
////            res.add("--fullscreen");
//
////        if (options.getProxy() != null && options.getProxy().type() == Proxy.Type.SOCKS) {
////            InetSocketAddress address = (InetSocketAddress) options.getProxy().address();
////            if (address != null) {
////                res.add("--proxyHost");
////                res.add(address.getHostString());
////                res.add("--proxyPort");
////                res.add(String.valueOf(address.getPort()));
////                if (StringUtils.isNotBlank(options.getProxyUser()) && StringUtils.isNotBlank(options.getProxyPass())) {
////                    res.add("--proxyUser");
////                    res.add(options.getProxyUser());
////                    res.add("--proxyPass");
////                    res.add(options.getProxyPass());
////                }
////            }
////        }
//
////        res.addAllWithoutParsing(Arguments.parseStringArguments(options.getGameArguments(), configuration));
//
////        res.removeIf(it -> getForbiddens().containsKey(it) && getForbiddens().get(it).get());
////        return new Command(res, tempNativeFolder, encoding);
//
//        return res;
//    }

//    protected void appendJvmArgs(CommandBuilder result) {
//
//    }
}
