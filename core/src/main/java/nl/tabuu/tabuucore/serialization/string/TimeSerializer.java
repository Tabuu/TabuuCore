package nl.tabuu.tabuucore.serialization.string;

public class TimeSerializer extends AbstractStringSerializer<Long> {

    @Override
    public String serialize(Long number) {
        if (number < 1) {
            return "0 ms";
        }

        StringBuffer timeBuf = new StringBuffer();

        long time = number / 1000;
        if (time < 1) {
            return timeBuf.toString();
        }

        long seconds = time % 60;
        prependTimeAndUnit(timeBuf, seconds, "s");

        // minute(60s) & above
        time = time / 60;
        if (time < 1) {
            return timeBuf.toString();
        }

        long minutes = time % 60;
        prependTimeAndUnit(timeBuf, minutes, "m");

        // hour(60m) & above
        time = time / 60;
        if (time < 1) {
            return timeBuf.toString();
        }

        long hours = time % 24;
        prependTimeAndUnit(timeBuf, hours, "h");

        // day(24h) & above
        time = time / 24;
        if (time < 1) {
            return timeBuf.toString();
        }

        long day = time % 365;
        prependTimeAndUnit(timeBuf, day, "d");

        // year(365d) ...
        time = time / 365;
        if (time < 1) {
            return timeBuf.toString();
        }

        prependTimeAndUnit(timeBuf, time, "y");

        return timeBuf.toString();
    }

    @Override
    public Long deserialize(String string) {
        long output = 0L;

        String[] args = string.split(" ");

        LongSerializer serializer = Serializer.LONG;

        for(String arg : args) {
            if(arg.endsWith("ms"))
                output += serializer.deserialize(arg.replace("ms", ""));

            else if(arg.endsWith("s"))
                output += serializer.deserialize(arg.replace("s", "")) * 1000L;

            else if(arg.endsWith("m"))
                output += serializer.deserialize(arg.replace("m", "")) * 60000L;

            else if(arg.endsWith("h"))
                output += serializer.deserialize(arg.replace("h", "")) * 3600000L;

            else if(arg.endsWith("d"))
                output += serializer.deserialize(arg.replace("d", "")) * 86400000L;

            else if(arg.endsWith("y"))
                output += serializer.deserialize(arg.replace("y", "")) * 30758400000L;
        }

        return output;
    }

    private void prependTimeAndUnit(StringBuffer timeBuf, long time, String unit) {
        if (time < 1) {
            return;
        }

        if (timeBuf.length() > 0) {
            timeBuf.insert(0, " ");
        }

        timeBuf.insert(0, unit);
        timeBuf.insert(0, time);
    }
}
