package nl.tabuu.tabuucore.serialization.string;

import nl.tabuu.tabuucore.serialization.bytes.Serializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Base64;
import java.util.Objects;

public class ConfigurationSerializableSerializer extends AbstractStringSerializer<ConfigurationSerializable> {

    @Override
    public String serialize(ConfigurationSerializable serializable) {
        return serializeArray(serializable);
    }

    @Override
    public ConfigurationSerializable deserialize(String string) {
        if(Objects.isNull(string)) return null;

        ConfigurationSerializable[] serializables = deserializeArray(string);
        if(serializables.length < 1)
            return null;
        return serializables[0];
    }

    @Override
    public ConfigurationSerializable[] deserializeArray(String string) {
        if(Objects.isNull(string)) return null;

        return Serializer.CONFIGURATION_SERIALIZABLE.deserialize(Base64.getDecoder().decode(string));
    }

    @Override
    public String serializeArray(ConfigurationSerializable... objects) {
        if(Objects.isNull(objects)) return null;

        return Base64.getEncoder().encodeToString(Serializer.CONFIGURATION_SERIALIZABLE.serialize(objects));
    }
}