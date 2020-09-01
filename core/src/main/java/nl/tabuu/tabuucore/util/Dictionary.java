package nl.tabuu.tabuucore.util;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.configuration.IConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Dictionary extends HashMap<String, String> {

    private boolean _usePlaceholderApi;

    public Dictionary(){
        super();

        IConfiguration config = TabuuCore.getInstance().getConfigurationManager().getConfiguration("config");
        _usePlaceholderApi = config.getBoolean("Dependencies.PlaceholderAPI.Enabled");
    }

    /**
     * Translates a configuration key to its value. The translation will be colored using the '&amp;' character as prefix character.
     * @param key the key.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String key, String... replacements){
        return translate(key, true, '&', replacements);
    }

    /**
     * Translates a configuration key to its value.
     * @param key the key.
     * @param useColor whether or not the string should be colored.
     * @param colorSymbol the prefix symbol used to define the color.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String key, boolean useColor, char colorSymbol, String... replacements) {
        String translation = get(key);

        if(translation == null)
            return key;

        for(int i = 0; i < replacements.length; i += 2){
            // Not an even amount of replacements
            if(i == replacements.length - 1)
                break;

            translation = translation.replace(replacements[i], replacements[i + 1]);
        }

        if(useColor)
            translation = ChatColor.translateAlternateColorCodes(colorSymbol, translation);

        return translation;
    }

    /**
     * Translates a configuration key to its value.
     * @param key the key.
     * @param player Player consumed by PlaceholderAPI.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String key, Player player, String... replacements){
        return translate(key, player, true, true, '&', replacements);
    }

    /**
     * Translates a configuration key to its value.
     * @param key the key.
     * @param player Player consumed by PlaceholderAPI.
     * @param usePlaceholderApi When true PlaceholderAPI replacements will automatically be used.
     * @param useColor whether or not the string should be colored.
     * @param colorSymbol the prefix symbol used to define the color.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String key, Player player, boolean usePlaceholderApi, boolean useColor, char colorSymbol, String... replacements){
        String translation = translate(key, useColor, colorSymbol, replacements);

        if(usePlaceholderApi && _usePlaceholderApi)
            translation = PlaceholderAPI.setPlaceholders(player, translation);

        return translation;
    }
}
