package nl.tabuu.tabuucore.api;

import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HologramAPI {

    private static HologramAPI INSTANCE;

    private List<IHologram> _holograms;

    protected HologramAPI(){
        _holograms = new ArrayList<>();
    }

    /**
     * Creates a new hologram visible to the specified player.
     * @param player the player the hologram is visible to.
     * @param location the location the hologram should be in.
     * @param lines the text lines of the hologram.
     * @return the created hologram.
     */
    public IHologram create(Player player, Location location, String... lines){
        IHologram hologram = create(location, false, lines);
        hologram.show(player);

        return hologram;
    }

    /**
     * Creates a new hologram.
     * @param location the location the hologram should be in.
     * @param display when set to true the created hologram will be displayed to everyone, else to no one.
     * @param lines the text lines of the hologram.
     * @return the created hologram.
     */
    public IHologram create(Location location, boolean display, String... lines){
        try {
            IHologram hologram =  (IHologram) NMSUtil.getWrapperClass("Hologram")
                    .getConstructor(Location.class, String[].class)
                    .newInstance(location, lines);

            if(display)
                Bukkit.getOnlinePlayers().forEach(hologram::show);

            _holograms.add(hologram);
            return hologram;
        }
        catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Purges the destroyed holograms and returns an unmodifiable collection of all active holograms.
     * @return an unmodifiable collection of all active holograms.
     */
    public Collection<IHologram> getHolograms(){
        _holograms.removeIf(IHologram::isDestroyed);
        return Collections.unmodifiableCollection(_holograms);
    }

    /**
     * Destroys the specified hologram.
     * @param hologram hologram to destroy.
     */
    public void destroy(IHologram hologram){
        hologram.destroy();
        _holograms.remove(hologram);
    }

    /**
     * Returns the HologramAPI instance.
     * @return the HologramAPI instance.
     */
    public static HologramAPI getInstance(){
        if(INSTANCE == null)
            INSTANCE = new HologramAPI();

        return INSTANCE;
    }

}
