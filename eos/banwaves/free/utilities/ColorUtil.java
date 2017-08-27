package eos.banwaves.free.utilities;

import org.bukkit.ChatColor;

/**
 * Created by zeljko on 4/23/2016.
 */
public class ColorUtil {

    public static String translateColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
