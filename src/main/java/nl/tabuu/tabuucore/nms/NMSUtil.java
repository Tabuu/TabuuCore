package nl.tabuu.tabuucore.nms;

import org.bukkit.Bukkit;

public class NMSUtil {

    public static String getVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
    }

    public static Class getWrapperClass(String className){
        try {
            return Class.forName("nl.tabuu.tabuucore.nms." + getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().severe("Class with name '" + className + "' was not found for your Spigot version!");
        }

        return null;
    }

}
