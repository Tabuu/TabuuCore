package nl.tabuu.tabuucore.serialization.string;

public class Serializer {

    public static CharacterSerializer       CHARACTER       = new CharacterSerializer();
    public static DoubleSerializer          DOUBLE          = new DoubleSerializer();
    public static IntegerSerializer         INTEGER         = new IntegerSerializer();
    public static LocationSerializer        LOCATION        = new LocationSerializer();
    public static LongSerializer            LONG            = new LongSerializer();
    public static OfflinePlayerSerializer   OFFLINE_PLAYER  = new OfflinePlayerSerializer();
    public static PlayerSerializer          PLAYER          = new PlayerSerializer();
    public static StringSerializer          STRING          = new StringSerializer();
    public static UUIDSerializer            UUID            = new UUIDSerializer();
    public static VectorSerializer          VECTOR          = new VectorSerializer();
    public static TimeSerializer            TIME            = new TimeSerializer();
}
