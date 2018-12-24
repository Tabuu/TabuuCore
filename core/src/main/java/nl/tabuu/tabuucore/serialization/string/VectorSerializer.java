package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.util.Vector;

public class VectorSerializer extends AbstractStringSerializer<Vector> {
    @Override
    public String serialize(Vector vector) {
        double
                x = vector.getX(),
                y = vector.getY(),
                z = vector.getZ();

        return Serializer.DOUBLE.serializeArray(x, y, z);
    }

    @Override
    public Vector deserialize(String string) {
        Double[] values = Serializer.DOUBLE.deserializeArray(string);
        return new Vector(values[0], values[1], values[2]);
    }
}
