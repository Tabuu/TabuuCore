package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;

public interface ITicksPerSecond {

    double getTPS(TPSTime time);

    default double getTPS(){
        return getTPS(TPSTime.ONE_MINUTE);
    }

    static ITicksPerSecond get(){
        try {
            return (ITicksPerSecond) NMSUtil.getWrapperClass("TicksPerSecond").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }

    enum TPSTime{
        ONE_MINUTE(0),
        FIVE_MINUTES(1),
        FIFTEEN_MINUTES(2);

        private int _index;
        TPSTime(int index){
            _index = index;
        }

        public int getIndex(){
            return _index;
        }
    }
}
