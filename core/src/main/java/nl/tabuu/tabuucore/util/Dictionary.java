package nl.tabuu.tabuucore.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.configuration.IConfiguration;
import nl.tabuu.tabuucore.text.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Dictionary extends HashMap<String, String> {

    private boolean _usePlaceholderApi;

    public Dictionary(){
        super();

        IConfiguration config = TabuuCore.getInstance().getConfigurationManager().getConfiguration("config.yml");
        _usePlaceholderApi = config.getBoolean("Dependencies.PlaceholderAPI.Enabled", false);
    }

    /**
     * Translates a configuration key to its value. The translation will be colored using the '&amp;' character as prefix character.
     * @param key the key.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String key, Object... replacements){
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
    public String translate(String key, boolean useColor, char colorSymbol, Object... replacements) {
        String translation = get(key);

        if(translation == null)
            return key;

        if(replacements.length % 2 != 0) throw new IllegalArgumentException("The replacements should come in pairs, and so result in an even number.");

        for(int i = 0; i < replacements.length; i += 2)
            translation = translation.replace(replacements[i].toString(), replacements[i + 1].toString());

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
    public String translate(String key, Player player, Object... replacements) {
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
    public String translate(String key, Player player, boolean usePlaceholderApi, boolean useColor, char colorSymbol, Object... replacements){
        String translation = translate(key, useColor, colorSymbol, replacements);

        if(usePlaceholderApi && _usePlaceholderApi)
            translation = PlaceholderAPI.setPlaceholders(player, translation);

        return translation;
    }

    public void send(Player player, String key, boolean usePlaceholderApi, boolean useColor, boolean useTextComponents, char colorSymbol, Object... replacements) {
        String message = translate(key, player, usePlaceholderApi, useColor, colorSymbol, replacements);

        if(useTextComponents) {
            BaseComponent[] components = ComponentBuilder.parse(message).build();
            player.spigot().sendMessage(components);
        } else player.sendMessage(message);
    }

    public void send(Player player, String key, Object... replacements) {
        send(player, key, true, true, true, '&', replacements);
    }
}
