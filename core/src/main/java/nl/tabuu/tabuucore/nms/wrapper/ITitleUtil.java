package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;

/**
 * Represents an API used for sending title packets.
 */
public interface ITitleUtil {

    /**
     * Sends a subtitle packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut);

    /**
     * Sends a title packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut);

    /**
     * Sends an actionbar packet to a player.
     * @param player the recipient of the title packet.
     * @param json the json containing the title's information.
     * @param fadeIn fade-in time in server ticks.
     * @param stay stay time in server ticks.
     * @param fadeOut fade-out time in server ticks.
     * @see <a href="http://minecraft.tools/en/title.php">http://minecraft.tools/en/title.php</a>
     */
    void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut);

    /**
     * Returns the TitleAPI wrapper class of the server NMS version.
     * @return the TitleAPI wrapper class of the server NMS version.
     */
    static ITitleUtil get(){
        try {
            return (ITitleUtil) NMSUtil.getWrapperClass("TitleUtil").getConstructor().newInstance();
        }
        catch (ReflectiveOperationException ignored) {
            throw new UnsupportedOperationException();
        }
    }
}
