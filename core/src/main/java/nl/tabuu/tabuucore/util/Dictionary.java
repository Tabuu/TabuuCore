package nl.tabuu.tabuucore.util;

import org.bukkit.ChatColor;

import java.util.HashMap;

public class Dictionary extends HashMap<String, String> {

    public String translate(String string, String... replacements){
        return translate(string, true, '&', replacements);
    }

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
