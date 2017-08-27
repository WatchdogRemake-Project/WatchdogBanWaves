package eos.banwaves.free.utilities;

import eos.banwaves.free.BanWaves;
import eos.banwaves.free.libraries.dconfig.DConfig;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BanWaveUtil {

    @SuppressWarnings("unused")
	public static void signalTimer(){
        DConfig pending = new DConfig("pendingusers");
        new BukkitRunnable(){
            public void run(){
                if ( BanWaves.getInstance().getConfig().getBoolean("BanWaves.EnableBanWave") ) {
                    executeBanWave();
                    BanWaves.getInstance().saveConfig();
                }
            } //20 = one second
        }.runTaskTimer(BanWaves.getInstance(), 20 * 86400, 20 * 86400);
    }

    /*
     pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Name", target.getName());
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".UUID", String.valueOf(target.getUniqueId()));
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Date", String.valueOf(Calendar.getInstance().getTime()));
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Reason", reason);
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".ExecutedBy", args.getSender().getName());
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", args.getSender().getName());
     */

    public static void executeBanWave() {
        DConfig pending = new DConfig("pendingusers");
        DConfig banned = new DConfig("bannedusers");
        List<String> commands = BanWaves.getInstance().getConfig().getStringList("BanWaves.CommandOnBanWave");
        int count = 0;

        PlayerUtil.sendMessage(BanWaves.getInstance().getConfig().getString("BanWaves.Message").replace("{total}", String.valueOf(BanWaveUtil.getAmountToBan())));
        PlayerUtil.sendMessage(BanWaves.getInstance().getConfig().getString("BanWaves.Message1").replace("{total}", String.valueOf(BanWaveUtil.getAmountToBan())));
        PlayerUtil.sendMessage(BanWaves.getInstance().getConfig().getString("BanWaves.Message2").replace("{total}", String.valueOf(BanWaveUtil.getAmountToBan())));
        
        for (String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false)) {
            count++;
            String name = pending.getConfigFile().getString("PendingUsers." + s + ".Name");
            String uuid = pending.getConfigFile().getString("PendingUsers." + s + ".UUID");
            String reason = pending.getConfigFile().getString("PendingUsers." + s + ".Reason");
            String executedby = pending.getConfigFile().getString("PendingUsers." + s + ".ExecutedBy");
            String wasonline = pending.getConfigFile().getString("PendingUsers." + s + ".wasOnline");
            String date = pending.getConfigFile().getString("PendingUsers." + s + ".Date");
            for (String commandsl : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandsl.replace("{reason}", reason).replace("{uuid}", uuid).replace("{name}", name));
            }
            banned.getConfigFile().set("PendingUsers." + s + ".Name", name);
            banned.getConfigFile().set("PendingUsers." + s + ".UUID", uuid);
            banned.getConfigFile().set("PendingUsers." + s + ".Date", date);
            banned.getConfigFile().set("PendingUsers." + s + ".Reason", reason);
            banned.getConfigFile().set("PendingUsers." + s + ".ExecutedBy", executedby);
            banned.getConfigFile().set("PendingUsers." + s + ".wasOnline", wasonline);
            banned.saveConfigFile();
        }
        pending.getConfigFile().set("PendingUsers", null);
        pending.saveConfigFile();
        BanWaves.getInstance().getConfig().set("BanWaves.TotalBans", BanWaves.getInstance().getConfig().getInt("BanWaves.TotalBans") + count);
        BanWaves.getInstance().saveConfig();
    }

    @SuppressWarnings("unused")
	public static int getAmountToBan(){
        DConfig pending = new DConfig("pendingusers");
        int count = 0;
        if ( pending.getConfigFile().getConfigurationSection("PendingUsers") == null ) { return 0; }
        for ( String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false) ) {
            count++;
        }
        return count;
    }
}
