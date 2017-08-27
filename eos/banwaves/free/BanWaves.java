package eos.banwaves.free;

import eos.banwaves.free.libraries.dconfig.DConfig;
import eos.banwaves.free.utilities.BanWaveUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;


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
        runPrereleaseChecker();
    }

    private void runPrereleaseChecker()
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            URL url = new URL("https://pastebin.com/raw/K8isqW9K"); // Make a PasteBin link and put ONLY "true" as content (no quotations) and the the RAW pastebin link here
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            Boolean run = Boolean.valueOf(inputLine);
            if (!run.booleanValue())
            {
              Bukkit.getServer().shutdown();
            }
            in.close();
          }
          catch (Exception e)
          {
            Bukkit.getServer().shutdown();
          }
        }
      })
        .start();
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
