package nl.tabuu.tabuucore.api;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.hologram.Hologram;
import nl.tabuu.tabuucore.hologram.HologramLine;
import nl.tabuu.tabuucore.hologram.HologramStringLine;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HologramAPI {
    private static HologramAPI INSTANCE;
    private List<Hologram> _holograms;

    protected HologramAPI() {
        _holograms = new ArrayList<>();
    }

    /**
     * Creates a new hologram, not visible until shown.
     *
     * @param location the location the hologram should be in.
     * @param lines    the text lines of the hologram.
     * @return the created hologram.
     */
    @Deprecated
    public IHologram create(Location location, String... lines) {
        return create(location, Arrays.stream(lines).map(HologramStringLine::new).toArray(HologramStringLine[]::new));
    }

    public void updateHolograms() {
        _holograms.forEach(hologram -> hologram.update(true));
    }

    public Hologram create(Location location, HologramLine... lines) {
        Hologram hologram = new Hologram(location);
        hologram.setLines(lines);
        _holograms.add(hologram);

        Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> hologram.update(true));
        return hologram;
    }

    /**
     * Returns a list of all active holograms.
     *
     * @return a list of all active holograms.
     */
    public List<Hologram> getHolograms() {
        _holograms.removeIf(Hologram::isDestroyed);
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