package nl.tabuu.tabuucore.api;

import nl.tabuu.tabuucore.nms.wrapper.ITitleUtil;
import org.bukkit.entity.Player;

public class TitleAPI {

    private static TitleAPI INSTANCE;

    private ITitleUtil _util;

    protected TitleAPI(){
        _util = ITitleUtil.get();
    }

    /**
     * Sends a subtitle packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    public void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut){
        _util.sendSubTitle(player, json, fadeIn, stay, fadeOut);
    }

    /**
     * Sends a title packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    public void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut){
        _util.sendTitle(player, json, fadeIn, stay, fadeOut);
    }

    /**
     * Sends an actionbar packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    public void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut){
        _util.sendActionbar(player, json, fadeIn, stay, fadeOut);
    }

    public static TitleAPI getInstance(){
        if(INSTANCE == null)
            INSTANCE = new TitleAPI();

        return INSTANCE;
    }
}
