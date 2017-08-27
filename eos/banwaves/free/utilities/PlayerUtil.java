package eos.banwaves.free.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void sendMessage(Player pl, String message) {

        if ( pl == null ) { ServerUtil.consoleColorSend(message); return; }
        pl.sendMessage(ColorUtil.translateColor(message));
    }

    public static void sendMessage(String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            sendMessage(players, message);
        }
    }


    public static Boolean isConsole(Player player){
        return player == null;
    }
}
