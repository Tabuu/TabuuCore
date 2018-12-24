package nl.tabuu.tabuucore.nms;

public enum NMSVersion {
    v1_8_R1(true, true),
    v1_8_R2(true, true),
    v1_8_R3(true, true),
    v1_9_R1(true, false),
    v1_9_R2(true, false),
    v1_10_R1(true, false),
    v1_11_R1(true, false),
    v1_12_R1(true, false),
    v1_13_R1(false, false),
    v1_13_R2(false, false);

    private boolean _legacy, _oldPvP;

    NMSVersion(boolean legacy, boolean oldPvP){
        _legacy = legacy;
        _oldPvP = oldPvP;
    }

    public boolean isLegacy(){
        return _legacy;
    }

    public boolean hasOldPvP(){
        return _oldPvP;
    }
}
