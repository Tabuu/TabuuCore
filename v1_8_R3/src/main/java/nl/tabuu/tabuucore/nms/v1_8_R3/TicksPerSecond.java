package nl.tabuu.tabuucore.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import nl.tabuu.tabuucore.nms.wrapper.ITicksPerSecond;

public class TicksPerSecond implements ITicksPerSecond {

    @Override
    public double getTPS(TPSTime time) {
        MinecraftServer server = MinecraftServer.getServer();
        return server.recentTps[time.getIndex()];
    }

}
