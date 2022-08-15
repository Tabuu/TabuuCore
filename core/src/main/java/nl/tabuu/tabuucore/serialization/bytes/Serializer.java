package nl.tabuu.tabuucore.serialization.bytes;

public class Serializer {
    public static final ItemStackSerializer                 ITEMSTACK                   = new ItemStackSerializer();
    public static final ConfigurationSerializableSerializer CONFIGURATION_SERIALIZABLE  = new ConfigurationSerializableSerializer();
    public static final ByteSerializer                      BYTE                        = new ByteSerializer();
    public static final ShortSerializer                     SHORT                       = new ShortSerializer();
    public static final IntegerSerializer                   INTEGER                     = new IntegerSerializer();
    public static final LongSerializer                      LONG                        = new LongSerializer();
    public static final FloatSerializer                     FLOAT                       = new FloatSerializer();
    public static final DoubleSerializer                    DOUBLE                      = new DoubleSerializer();
    public static final ByteArraySerializer                 BYTE_ARRAY                  = new ByteArraySerializer();
    public static final StringSerializer                    STRING                      = new StringSerializer();
    public static final IntegerArraySerializer              INTEGER_ARRAY               = new IntegerArraySerializer();
    public static final LongArraySerializer                 LONG_ARRAY                  = new LongArraySerializer();
}
