package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class LocationSerializer extends AbstractStringSerializer<Location> {
    @Override
    public String serialize(Location location) {
        if(Objects.isNull(location)) return null;

        String world = location.getWorld().getName().replace(" ", "|");

        DoubleSerializer doubleSerializer = Serializer.DOUBLE;

        String
                x = doubleSerializer.serialize(location.getX()),
                y = doubleSerializer.serialize(location.getY()),
                z = doubleSerializer.serialize(location.getZ());

        String
                yaw = doubleSerializer.serialize((double) location.getYaw()),
                pitch = doubleSerializer.serialize((double) location.getPitch());

        if(location.getYaw() == 0 && location.getPitch() == 0)
            return world + " " + x + " " + y + " " + z;
        else
            return world + " " + x + " " + y + " " + z + " " + yaw + " " + pitch;
    }

    @Override
    public Location deserialize(String string) {
        if(Objects.isNull(string)) return null;

        DoubleSerializer doubleSerializer = Serializer.DOUBLE;
        String[] args = string.split(" ");

        if(args.length < 4)
            return null;

        World world = Bukkit.getWorld(args[0].replace("|", " "));
        Double
                x = doubleSerializer.deserialize(args[1]),
                y = doubleSerializer.deserialize(args[2]),
                z = doubleSerializer.deserialize(args[3]);

        Double yaw = 0d, pitch = 0d;

        if (args.length > 4) {
            yaw = doubleSerializer.deserialize(args[4]);
            pitch = doubleSerializer.deserialize(args[5]);
        }

        if(x == null || y == null || z == null || yaw == null || pitch == null)
            return null;

        return new Location(world, x, y, z, yaw.floatValue(), pitch.floatValue());
    }
}
