package nl.tabuu.tabuucore.nms;

import nl.tabuu.tabuucore.nms.wrapper.IAnvilUtil;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class NMSUtil {

    public static String getVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
    }

    public static IAnvilUtil getAnvilUtil(){

        try {
            return (IAnvilUtil) getWrapperClass("AnvilUtil").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

        }

        return null;
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
