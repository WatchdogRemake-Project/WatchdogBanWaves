package eos.banwaves.free.utilities;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    public static long getESTTime(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date d = new Date();
        return d.getTime();
    }

    public static String timeLeft(long time, boolean extend)
    {
        time /= 1000L;
        int days = (int)(time / 86400L);
        time -= 86400 * days;
        int hours = (int)(time / 3600L);
        time -= 3600 * hours;
        int minutes = (int)(time / 60L);
        time -= 60 * minutes;
        int seconds = (int)time;

        StringBuilder sb = new StringBuilder();
        if (days != 0) {
            sb.append(days).append(" day").append(days == 1 ? " " : "s ");
        }
        if (hours != 0) {
            sb.append(hours).append(" hour").append(hours == 1 ? " " : "s ");
        }
        if (minutes != 0) {
            sb.append(minutes).append(" minute").append(minutes == 1 ? " " : "s ");
        }

        if ( extend ) {
            if (seconds != 0) {
                sb.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
            }
        }
        return sb.toString().trim();
    }

    public static String timeLeft(Player pl, long time, boolean extend)
    {
        time /= 1000L;
        int days = (int)(time / 86400L);
        time -= 86400 * days;
        int hours = (int)(time / 3600L);
        time -= 3600 * hours;
        int minutes = (int)(time / 60L);
        time -= 60 * minutes;
        int seconds = (int)time;

        StringBuilder sb = new StringBuilder();
        if (days != 0) {
            sb.append(days).append(" day").append(days == 1 ? " " : "s ");
        }
        if (hours != 0) {
            sb.append(hours).append(" hour").append(hours == 1 ? " " : "s ");
        }
        if (minutes != 0) {
            sb.append(minutes).append(" minute").append(minutes == 1 ? " " : "s ");
        }

        if ( extend ) {
            if (seconds != 0) {
                sb.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
            }
        } else {

            /*
            todo: Fix This
            if ( AccountMap.getValue(String.valueOf(pl.getUniqueId())).getPlaytime() < 60000 ) {
                if (seconds != 0) {
                    sb.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
                }
            }
            */

        }
        return sb.toString().trim();
    }

}
