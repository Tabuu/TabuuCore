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
     * Creates a new hologram, not visible until shown.
     * @param location the location the hologram should be in.
     * @param lines the text lines of the hologram.
     * @return the created hologram.
     */
    public IHologram create(Location location, String... lines){
        try {
            IHologram hologram =  (IHologram) NMSUtil.getWrapperClass("Hologram")
                    .getConstructor(Location.class, String[].class)
                    .newInstance(location, lines);

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
