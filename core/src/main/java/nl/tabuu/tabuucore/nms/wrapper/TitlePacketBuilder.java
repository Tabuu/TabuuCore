package nl.tabuu.tabuucore.nms.wrapper;

import net.md_5.bungee.api.chat.BaseComponent;
import nl.tabuu.tabuucore.debug.Debug;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.text.ComponentBuilder;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class TitlePacketBuilder {

    private String _title, _subTitle, _actionBar;
    private int _fadeIn = 10, _stay = 70, _fadeOut = 20; // Default values from CraftBukkit source.

    /**
     * Returns a consumer that sends title packets to the accepted player.
     * @return a consumer that sends title packets to the accepted player.
     */
    public Consumer<Player> getTitlePacket() {
        return player -> {
            sendTimings(player, getFadeIn(), getStay(), getFadeOut());

            if(Objects.nonNull(getTitle()) && !getTitle().isEmpty())
                sendTitlePacket(player, getTitle());

            if(Objects.nonNull(getSubTitle()) && !getSubTitle().isEmpty())
                sendSubTitlePacket(player, getSubTitle());

            if(Objects.nonNull(getActionBar()) && !getActionBar().isEmpty())
                sendActionBar(player, getActionBar());
        };
    }

    /**
     * Returns a consumer that sends title reset packets to the accepted player.
     * @return a consumer that sends title reset packets to the accepted player.
     */
    public Consumer<Player> getTitleClearPacket() {
        return this::sendReset;
    }

    protected abstract void sendReset(Player player);

    protected abstract void sendActionBar(Player player, String json);

    protected abstract void sendTitlePacket(Player player, String json);

    protected abstract void sendSubTitlePacket(Player player, String json);

    protected abstract void sendTimings(Player player, int fadeIn, int stay, int fadeOut);

    /**
     * Sets the timings of the (sub-)titles. The action bar is unaffected.
     * @param fadeIn The amount of ticks it takes for the (sub-)titles to fade in.
     * @param stay The amount of ticks the (sub-)titles will remain on screen after fade in, and before fade out.
     * @param fadeOut The amount of ticks it takes for the (sub-)titles to fade out.
     * @return This builder.
     */
    public TitlePacketBuilder setTimings(int fadeIn, int stay, int fadeOut) {
        _fadeIn = fadeIn;
        _stay = stay;
        _fadeOut = fadeOut;
        return this;
    }

    protected int getFadeIn() {
        return _fadeIn;
    }

    public int getStay() {
        return _stay;
    }

    public int getFadeOut() {
        return _fadeOut;
    }

    /**
     * Sets the value of the title as json.
     * @param json The value of the title as json.
     * @return This builder
     */
    public TitlePacketBuilder setTitle(String json) {
        _title = json;
        return this;
    }

    /**
     * Sets the value of the title as chat base components.
     * These chat base components can easily be create by TabuuCore with the {@link ComponentBuilder}
     * @param components The value of the title as chat base components.
     * @return This builder
     */
    public TitlePacketBuilder setTitle(BaseComponent[] components) {
        _title = ComponentBuilder.create().then(components).toString();
        return this;
    }

    protected String getTitle() {
        return _title;
    }

    /**
     * Sets the value of the sub-title as json.
     * @param json The value of the sub-title as json.
     * @return This builder
     */
    public TitlePacketBuilder setSubTitle(String json) {
        _subTitle = json;
        return this;
    }

    /**
     * Sets the value of the sub-title as chat base components.
     * These chat base components can easily be create by TabuuCore with the {@link ComponentBuilder}
     * @param components The value of the sub-title as chat base components.
     * @return This builder
     */
    public TitlePacketBuilder setSubTitle(BaseComponent[] components) {
        _subTitle = ComponentBuilder.create().then(components).toString();
        return this;
    }

    protected String getSubTitle() {
        return _subTitle;
    }

    /**
     * Sets the value of the action bar as json.
     * @param json The value of the action bar as json.
     * @return This builder
     */
    public TitlePacketBuilder setActionBar(String json) {
        _actionBar = json;
        return this;
    }

    /**
     * Sets the value of the action bar as chat base components.
     * These chat base components can easily be create by TabuuCore with the {@link ComponentBuilder}
     * @param components The value of the action bar as chat base components.
     * @return This builder
     */
    public TitlePacketBuilder setActionBar(BaseComponent[] components) {
        _actionBar = ComponentBuilder.create().then(components).toString();
        return this;
    }

    protected String getActionBar() {
        return _actionBar;
    }

    /**
     * Returns the TitlePacketBuilder wrapper class of the server NMS version.
     * @return the TitlePacketBuilder wrapper class of the server NMS version.
     */
    public static TitlePacketBuilder get() {
        try {
            return (TitlePacketBuilder) NMSUtil.getWrapperClass("TitlePacketBuilder").getConstructor().newInstance();
        }
        catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}