package nl.tabuu.tabuucore.api;

import nl.tabuu.tabuucore.hologram.HologramLine;
import nl.tabuu.tabuucore.hologram.HologramStringLine;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HologramAPI {

    private static HologramAPI INSTANCE;

    private List<IHologram> _holograms;
    private Constructor<?> _hologramConstructor;

    protected HologramAPI() {
        _holograms = new ArrayList<>();

        try {
            _hologramConstructor = NMSUtil.getWrapperClass("Hologram").getConstructor(Location.class, HologramLine[].class);
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not find wrapper class!", e);
        }
    }

    /**
     * Creates a new hologram, not visible until shown.
     *
     * @param location the location the hologram should be in.
     * @param lines    the text lines of the hologram.
     * @return the created hologram.
     */
    public IHologram create(Location location, String... lines) {
        return create(location, Arrays.stream(lines).map(HologramStringLine::new).toArray(HologramStringLine[]::new));
    }

    public IHologram create(Location location, HologramLine... lines) {
        try {
            IHologram hologram = (IHologram) _hologramConstructor.newInstance(location, lines);
            _holograms.add(hologram);
            return hologram;
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!", e);
        }
    }

    /**
     * Returns a list of all active holograms.
     *
     * @return a list of all active holograms.
     */
    public List<IHologram> getHolograms() {
        _holograms.removeIf(IHologram::isDestroyed);
        return Collections.unmodifiableList(_holograms);
    }

    /**
     * Destroys the specified hologram.
     *
     * @param hologram hologram to destroy.
     */
    public void destroy(IHologram hologram) {
        hologram.destroy();
        _holograms.remove(hologram);
    }

    /**
     * Returns the HologramAPI instance.
     *
     * @return the HologramAPI instance.
     */
    public static HologramAPI getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HologramAPI();

        return INSTANCE;
    }

}
