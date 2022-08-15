package nl.tabuu.tabuucore.chat.hook;

import net.milkbowl.vault.chat.Chat;
import nl.tabuu.tabuucore.TabuuCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Chat CHAT;

    public static Chat getChat(){
        if(CHAT == null){
            if(Bukkit.getPluginManager().getPlugin("Vault") == null){
                TabuuCore.getInstance().getLogger().severe("Could not find the Vault plugin!");
                return null;
            }
            else{
                RegisteredServiceProvider<Chat> registeredServiceProvider = TabuuCore.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
                if(registeredServiceProvider == null){
                    TabuuCore.getInstance().getLogger().severe("Could not find vault service provider (you probably do not have an economy plugin installed).");
                    return null;
                }

                CHAT = registeredServiceProvider.getProvider();
                if(CHAT == null) {
                    TabuuCore.getInstance().getLogger().severe("Could not get Vault service provider!");
                    return null;
                }
            }
        }
        return CHAT;
    }

}
