package nl.tabuu.tabuucore.debug;

import org.bukkit.Bukkit;

public class Debug {

    public static void log(Object object){
        int length = Thread.currentThread().getStackTrace().length;
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];

        String prefix = "[" + element.getFileName() + ":" + element.getLineNumber() +"]";

        Bukkit.broadcastMessage(prefix + " " + object);
    }

}
