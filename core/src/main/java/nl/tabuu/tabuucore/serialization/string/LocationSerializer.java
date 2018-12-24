package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer extends AbstractStringSerializer<Location> {
    @Override
    public String serialize(Location location) {
        String world = location.getWorld().getName().replace(" ", "|");

        double
                x = location.getX(),
                y = location.getY(),
                z = location.getZ();

        float
                yaw = location.getYaw(),
                pitch = location.getPitch();

        if(yaw == 0 && pitch == 0)
            return world + " " + x + " " + y + " " + z;
        else
            return world + " " + x + " " + y + " " + z + " " + yaw + " " + pitch;
    }

    @Override
    public Location deserialize(String string) {
        DoubleSerializer doubleSerializer = new DoubleSerializer();
        String[] args = string.split(" ");

        if(args.length < 4)
            return null;

        World world = Bukkit.getWorld(args[0].replace("|", " "));
        double
                x = doubleSerializer.deserialize(args[1]),
                y = doubleSerializer.deserialize(args[2]),
                z = doubleSerializer.deserialize(args[3]);

        float yaw = 0f, pitch = 0f;

        if (args.length > 4) {
            yaw = Float.parseFloat(args[4]);
            pitch = Float.parseFloat(args[5]);
        }

        return new Location(world, x, y, z, yaw, pitch);
    }
}
