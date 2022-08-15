package nl.tabuu.tabuucore.api;

import nl.tabuu.tabuucore.nms.wrapper.TitlePacketBuilder;
import org.bukkit.entity.Player;

@Deprecated
public class TitleAPI {

    private static TitleAPI INSTANCE;

    /**
     * Sends a subtitle packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    public void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        TitlePacketBuilder.get()
                .setTimings(fadeIn, stay, fadeOut)
                .setSubTitle(json)
                .getTitlePacket()
                .accept(player);
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
    public void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        TitlePacketBuilder.get()
                .setTimings(fadeIn, stay, fadeOut)
                .setTitle(json)
                .getTitlePacket()
                .accept(player);
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
    public void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut) {
        TitlePacketBuilder.get()
                .setTimings(fadeIn, stay, fadeOut)
                .setActionBar(json)
                .getTitlePacket()
                .accept(player);
    }

    public static TitleAPI getInstance(){
        if(INSTANCE == null)
            INSTANCE = new TitleAPI();

        return INSTANCE;
    }
}