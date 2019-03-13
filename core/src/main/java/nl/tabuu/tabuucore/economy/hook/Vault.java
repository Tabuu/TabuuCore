package nl.tabuu.tabuucore.economy.hook;

import net.milkbowl.vault.economy.Economy;
import nl.tabuu.tabuucore.TabuuCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Economy _economy;

    /**
     * Checks if vault economy has already been initialized and if not tries to initialize it.
     * @return Vault economy.
     */
    public static Economy getEconomy(){
        if(_economy == null){
            if(Bukkit.getPluginManager().getPlugin("Vault") == null)
                TabuuCore.getInstance().getLogger().severe("Could not find the Vault plugin!");
            else{
                RegisteredServiceProvider<Economy> registeredServiceProvider = TabuuCore.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
                if(registeredServiceProvider == null){
                    TabuuCore.getInstance().getLogger().severe("Could not find vault service provider (you probably do not have an economy plugin installed).");
                    return null;
                }

                _economy = registeredServiceProvider.getProvider();
                if(_economy == null)
                    TabuuCore.getInstance().getLogger().severe("Could not get Vault service provider!");
            }
        }
        return _economy;
    }

}
