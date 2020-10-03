package nl.tabuu.tabuucore.nms.v1_15_R1;

import net.minecraft.server.v1_15_R1.MinecraftServer;
import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.nms.wrapper.ITicksPerSecond;

import java.lang.reflect.Field;

public class TicksPerSecond implements ITicksPerSecond {

    private Field _tpsField;
    private final String ERROR_RETRIEVING = "Could not retrieve TPS from the Minecraft server.";

    public TicksPerSecond(){
        try {
            _tpsField = MinecraftServer.class.getField("recentTps");
        } catch (NoSuchFieldException e) {
            TabuuCore.getInstance().getLogger().severe(ERROR_RETRIEVING);
        }
    }

    @Override
    public double getTPS(TPSTime time) {
        MinecraftServer server = MinecraftServer.getServer();
        try {
            double[] tpsArray = (double[]) _tpsField.get(server);
            return tpsArray[time.ordinal()];
        } catch (IllegalAccessException e) {
            TabuuCore.getInstance().getLogger().severe(ERROR_RETRIEVING);
        }
        return 0.0d;
    }

}
