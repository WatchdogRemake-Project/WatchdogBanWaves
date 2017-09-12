package eos.banwaves.free;

import eos.banwaves.free.libraries.dconfig.DConfig;
import eos.banwaves.free.utilities.BanWaveUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class BanWaves extends JavaPlugin {

    public void onEnable(){
        new BanWaveCommands();
        copyConfig();
        createBannedUsersConfig();
        createPendingUsers();

        if ( BanWaves.getInstance().getConfig().getLong("BanWaves.StoredTime") <= 0 ) {
            long time = getConfig().getLong("BanWaves.Time") + System.currentTimeMillis();
            getConfig().set("BanWaves.StoredTime", time);
            saveConfig();
        }
        BanWaveUtil.signalTimer();
    }
    
    public void onDisable(){}

    public void copyConfig(){
        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();
    }

    public void createBannedUsersConfig(){
        DConfig banfile = new DConfig("bannedusers");
        banfile.makeConfigFile();
    }
    public void createPendingUsers(){
        DConfig pending = new DConfig("pendingusers");
        pending.makeConfigFile();
    }

    public static BanWaves getInstance(){
        return BanWaves.getPlugin(BanWaves.class);
    }

}
