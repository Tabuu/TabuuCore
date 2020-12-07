package nl.tabuu.tabuucore.hologram;

import net.md_5.bungee.api.chat.BaseComponent;
import nl.tabuu.tabuucore.text.ComponentBuilder;
import org.bukkit.entity.Player;

public class HologramNameTagLine extends SingleStandHologramLine {

    private BaseComponent[] _nameTag;

    public HologramNameTagLine(BaseComponent[] nameTag) {
        _nameTag = nameTag;
    }

    public HologramNameTagLine(String string) {
        _nameTag = ComponentBuilder.create().thenText(string).build();
    }

    public BaseComponent[] getNameTag() {
        return _nameTag;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        getStand().setCustomName(getNameTag());
        getStand().setCustomNameVisible(true);
    }

    @Override
    public boolean recycle(HologramLine line) {
        if(!(line instanceof HologramNameTagLine)) return false;

        HologramNameTagLine nameTagLine = (HologramNameTagLine) line;
        _nameTag = nameTagLine.getNameTag();

        return true;
    }

    @Override
    public double getTopSpacing() {
        return 0.125D;
    }

    @Override
    public double getBottomSpacing() {
        return 0.125D;
    }

    @Override
    public HologramLine clone() {
        return new HologramNameTagLine(getNameTag());
    }
}