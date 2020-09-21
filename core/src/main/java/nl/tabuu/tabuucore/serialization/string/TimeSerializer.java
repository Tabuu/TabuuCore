package nl.tabuu.tabuucore.serialization.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeSerializer extends AbstractStringSerializer<Long> {

    private Pattern _regex;

    public TimeSerializer() {
        _regex = Pattern.compile("(?<count>\\d+)(?<type>(ms|m|h|d|y|s))");

    }

    @Override
    public String serialize(Long number) {
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
        Long time = null;
        Matcher match = _regex.matcher(string);

        while(match.find()) {
            if(time == null) time = 0L;
            String countString = match.group("count");
            String type = match.group("type");

            Long count = Serializer.LONG.deserialize(countString);
            if(count == null) count = 0L;

            switch (type) {
                case "ms":
                    time += count;
                    break;

                case "s":
                    time += count * 1000L;
                    break;

                case "m":
                    time += count * 60000L;
                    break;

                case "h":
                    time += count * 3600000L;
                    break;

                case "d":
                    time += count * 86400000L;
                    break;

                case "y":
                    time += count * 31536000000L;
                    break;
            }
        }

        return time;
    }

    private void prependTimeAndUnit(StringBuffer timeBuf, long time, String unit) {
        if (time < 1)
            return;

        if (timeBuf.length() > 0) {
            timeBuf.insert(0, " ");
        }

        timeBuf.insert(0, unit);
        timeBuf.insert(0, time);
    }
}
