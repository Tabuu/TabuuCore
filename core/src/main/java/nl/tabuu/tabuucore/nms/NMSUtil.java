package nl.tabuu.tabuucore.nms;

import org.bukkit.Bukkit;

public class NMSUtil {

    private static NMSVersion _nmsVersion;

    public static NMSVersion getVersion(){
        if(_nmsVersion == null){
            String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
            _nmsVersion = NMSVersion.valueOf(versionName);
        }
        return _nmsVersion;
    }

    public static Class<?> getWrapperClass(String className, NMSVersion version){
        try {
            return Class.forName("nl.tabuu.tabuucore.nms." + version.name() + "." + className);
        }
        catch (ClassNotFoundException e) {
            Bukkit.getLogger().severe("Class with name '" + className + "' was not found for your Spigot version!");
            e.printStackTrace();
        }

        return null;
    }

    public static Class<?> getWrapperClass(String className){
        return getWrapperClass(className, getVersion());
    }

    public static Class<?> getNMSClass(String className, NMSVersion version){
        try{
            return Class.forName("net.minecraft.server." + version.name() + "." + className);
        }
        catch(ClassNotFoundException e){
            return null;
        }
    }

    public static Class<?> getNMSClass(String className){
        return getNMSClass(className, getVersion());
    }


}
