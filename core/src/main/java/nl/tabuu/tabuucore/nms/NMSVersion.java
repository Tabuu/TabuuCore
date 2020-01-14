package nl.tabuu.tabuucore.nms;

import org.bukkit.plugin.PluginDescriptionFile;

public enum NMSVersion {
    /** Used to indicate a NMS version not supported by this enum.*/
    UNKNOWN(false, null),

    /** Latest used by Minecraft version 1.8*/
    v1_8_R1(false, null),

    /** Latest used by Minecraft version 1.8.3*/
    v1_8_R2(false, null),

    /** Latest used by Minecraft version 1.8.9*/
    v1_8_R3(true, null),

    /** Latest used by Minecraft version 1.9*/
    v1_9_R1(false, null),

    /** Latest used by Minecraft version 1.9.4*/
    v1_9_R2(false, null),

    /** Latest used by Minecraft version 1.10.2*/
    v1_10_R1(false, null),

    /** Latest used by Minecraft version 1.11.2*/
    v1_11_R1(false, null),

    /** Latest used by Minecraft version 1.12.2*/
    v1_12_R1(true, null),

    /** Latest used by Minecraft version 1.13*/
    v1_13_R1(false, "1.13"),

    /** Latest used by Minecraft version 1.13.2*/
    v1_13_R2(true, "1.13"),

    /** Latest used by Minecraft version 1.14.4*/
    v1_14_R1(true, "1.14"),

    /** Latest used by Minecraft version 1.15.1*/
    v1_15_R1(true, "1.15");

    private boolean _supported;
    private String _spigotAPIVersion;

    NMSVersion(boolean supported, String spigotAPIVersion){
        _supported = supported;
        _spigotAPIVersion = spigotAPIVersion;
    }

    /**
     * Compares two enums and returns whether the NMS version was released later than the given enum.
     * @param other the enum that will be compared against the current enum.
     * @return whether the NMS version was released later than the given enum.
     */
    public boolean isPost(NMSVersion other){
        return other.ordinal() < ordinal();
    }

    /**
     * Compares two enums and returns whether the NMS version was released earlier than the given enum.
     * @param other the enum that will be compared against the current enum.
     * @return whether the NMS version was released earlier than the given enum.
     */
    public boolean isPre(NMSVersion other){
        return other.ordinal() > ordinal();
    }

    /**
     * Compares two enums and returns whether the NMS version was released later or is equal to the given enum.
     * @param other the enum that will be compared against the current enum.
     * @return whether the NMS version was released later or is equal to the given enum.
     */
    public boolean isPostOrEquals(NMSVersion other){
        return isPost(other) || equals(other);
    }

    /**
     * Compares two enums and returns whether the NMS version was released earlier or is equal to the given enum.
     * @param other the enum that will be compared against the current enum.
     * @return whether the NMS version was released earlier or is equal to the given enum.
     */
    public boolean isPreOrEquals(NMSVersion other){
        return isPre(other) || equals(other);
    }

    /**
     * Returns whether or not this NMS version is supported by TabuuCore.
     * @return whether or not this NMS version is supported by TabuuCore.
     */
    public boolean isSupported(){
        return _supported;
    }

    /**
     * Returns the spigot API version which is also returned by {@link PluginDescriptionFile#getAPIVersion()}.
     * @return the spigot API version which is also returned by {@link PluginDescriptionFile#getAPIVersion()}.
     */
    public String getSpigotAPIVersion(){
        return _spigotAPIVersion;
    }
}
