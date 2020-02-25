package nl.tabuu.tabuucore.serialization.string;

public class Serializer {

    public static final CharacterSerializer       CHARACTER       = new CharacterSerializer();
    public static final BooleanSerializer         BOOLEAN         = new BooleanSerializer();
    public static final DoubleSerializer          DOUBLE          = new DoubleSerializer();
    public static final IntegerSerializer         INTEGER         = new IntegerSerializer();
    public static final LocationSerializer        LOCATION        = new LocationSerializer();
    public static final LongSerializer            LONG            = new LongSerializer();
    public static final OfflinePlayerSerializer   OFFLINE_PLAYER  = new OfflinePlayerSerializer();
    public static final PlayerSerializer          PLAYER          = new PlayerSerializer();
    public static final StringSerializer          STRING          = new StringSerializer();
    public static final UUIDSerializer            UUID            = new UUIDSerializer();
    public static final VectorSerializer          VECTOR          = new VectorSerializer();
    public static final TimeSerializer            TIME            = new TimeSerializer();
    public static final ItemStackSerializer       ITEMSTACK       = new ItemStackSerializer();
}
