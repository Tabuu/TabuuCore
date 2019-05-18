package nl.tabuu.tabuucore.nms;

import nl.tabuu.tabuucore.combat.CombatType;
import nl.tabuu.tabuucore.material.MaterialType;

public enum NMSVersion {
    /** Used to indicate a NMS version not supported by this enum.*/
    UNKNOWN(false, null, null),

    /** Latest used by Minecraft version 1.8*/
    v1_8_R1(false, CombatType.LEGACY, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.8.3*/
    v1_8_R2(false, CombatType.LEGACY, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.8.9*/
    v1_8_R3(true, CombatType.LEGACY, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.9*/
    v1_9_R1(false, CombatType.POST_1_8, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.9.4*/
    v1_9_R2(false, CombatType.POST_1_8, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.10.2*/
    v1_10_R1(false, CombatType.POST_1_8, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.11.2*/
    v1_11_R1(false, CombatType.POST_1_8, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.12.2*/
    v1_12_R1(true, CombatType.POST_1_8, MaterialType.LEGACY),

    /** Latest used by Minecraft version 1.13*/
    v1_13_R1(false, CombatType.POST_1_8, MaterialType.POST_1_12),

    /** Latest used by Minecraft version 1.13.2*/
    v1_13_R2(true, CombatType.POST_1_8, MaterialType.POST_1_12),

    /** Latest used by Minecraft version 1.14*/
    v1_14_R1(true, CombatType.POST_1_8, MaterialType.POST_1_12);

    private boolean _supported;
    private CombatType _combatType;
    private MaterialType _materialType;

    NMSVersion(boolean supported, CombatType combatType, MaterialType materialType){
        _supported = supported;
        _combatType = combatType;
        _materialType = materialType;
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
     * Returns the {@link CombatType} used by this NMS version.
     * @deprecated not maintainable.
     * @return the combat type used by this NMS version.
     */
    @Deprecated
    public CombatType getCombatType(){
        return _combatType;
    }

    /**
     * Returns the {@link MaterialType} used by this NMS version.
     * @deprecated not maintainable.
     * @return the combat type used by this NMS version.
     */
    @Deprecated
    public MaterialType getMaterialType(){
        return _materialType;
    }
}
