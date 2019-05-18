package nl.tabuu.tabuucore.util;

import org.bukkit.ChatColor;

import java.util.HashMap;

public class Dictionary extends HashMap<String, String> {

    /**
     * Translates a configuration key to its value. The translation will be colored using the '&' character as prefix character.
     * @param string the key.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String string, String... replacements){
        return translate(string, true, '&', replacements);
    }

    /**
     * Translates a configuration key to its value.
     * @param string the key.
     * @param useColor whether or not the string should be colored.
     * @param colorSymbol the prefix symbol used to define the color.
     * @param replacements replaces the fist string (index n) with the next string (index n + 1).
     * @return translated string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translate(String string, boolean useColor, char colorSymbol, String... replacements) {

        String translation = get(string);

        if(translation == null)
            return string;

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
}
