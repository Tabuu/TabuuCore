package nl.tabuu.tabuucore.nms;

import nl.tabuu.tabuucore.combat.CombatType;
import nl.tabuu.tabuucore.material.MaterialType;

public enum NMSVersion {
    v1_8_R1(CombatType.LEGACY, MaterialType.LEGACY),
    v1_8_R2(CombatType.LEGACY, MaterialType.LEGACY),
    v1_8_R3(CombatType.LEGACY, MaterialType.LEGACY),
    v1_9_R1(CombatType.POST_1_8, MaterialType.LEGACY),
    v1_9_R2(CombatType.POST_1_8, MaterialType.LEGACY),
    v1_10_R1(CombatType.POST_1_8, MaterialType.LEGACY),
    v1_11_R1(CombatType.POST_1_8, MaterialType.LEGACY),
    v1_12_R1(CombatType.POST_1_8, MaterialType.LEGACY),
    v1_13_R1(CombatType.POST_1_8, MaterialType.POST_1_12),
    v1_13_R2(CombatType.POST_1_8, MaterialType.POST_1_12);

    private CombatType _combatType;
    private MaterialType _materialType;

    NMSVersion(CombatType combatType, MaterialType materialType){
        _combatType = combatType;
        _materialType = materialType;
    }

    public CombatType getCombatType(){
        return _combatType;
    }

    public MaterialType getMaterialType(){
        return _materialType;
    }

    public boolean isPost(NMSVersion other){
        return other.ordinal() < ordinal();
    }

    public boolean isPre(NMSVersion other){
        return other.ordinal() > ordinal();
    }

    public boolean isPostOrEquals(NMSVersion other){
        return isPost(other) || equals(other);
    }

    public boolean isPreOrEquals(NMSVersion other){
        return isPre(other) || equals(other);
    }
}
