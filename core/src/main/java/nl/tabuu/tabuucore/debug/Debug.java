package nl.tabuu.tabuucore.debug;

import org.bukkit.Bukkit;

public class Debug {

    public static void log(Object object){
        Bukkit.broadcastMessage(getPrefix() + object.toString());
    }

    private static String getPrefix(){
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        return String.format("[%s:%s] ", element.getFileName(), element.getLineNumber());
    }
}
