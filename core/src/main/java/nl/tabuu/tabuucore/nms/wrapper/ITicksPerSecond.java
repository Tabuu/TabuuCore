package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;

public interface ITicksPerSecond {

    /**
     * Returns the average TPS over the provided {@link TPSTime} time period.
     * @param time the time period used to get the average TPS.
     * @return the TPS based on the provided {@link TPSTime}.
     */
    double getTPS(TPSTime time);

    /**
     * Returns the average TPS over the {@link TPSTime#ONE_MINUTE} time period.
     * @return e average TPS over the {@link TPSTime#ONE_MINUTE} time period.
     */
    default double getTPS(){
        return getTPS(TPSTime.ONE_MINUTE);
    }

    /**
     * Returns the TicksPerSecond wrapper class of the server NMS version.
     * @return the TicksPerSecond wrapper class of the server NMS version.
     */
    static ITicksPerSecond get(){
        try {
            return (ITicksPerSecond) NMSUtil.getWrapperClass("TicksPerSecond").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }

    /**
     * An time enum used to get the average TPS over that time period.
     */
    enum TPSTime{
        ONE_MINUTE,
        FIVE_MINUTES,
        FIFTEEN_MINUTES;

        @Deprecated
        public int getIndex(){
            return ordinal();
        }
    }
}
