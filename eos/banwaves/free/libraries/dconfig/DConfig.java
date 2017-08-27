package eos.banwaves.free.libraries.dconfig;

import eos.banwaves.free.BanWaves;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by zeljko on 8/1/2016.
 */
public class DConfig {

    File userFile;
    FileConfiguration userConfig;

    public DConfig(String name){
        userFile = new File(BanWaves.getInstance().getDataFolder() + File.separator, name + ".yml");
        userConfig = YamlConfiguration.loadConfiguration(userFile);
    }

    public void makeConfigFile(){
        if ( !userFile.exists() ) {
            try {
                userConfig.save(userFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public FileConfiguration getConfigFile(){
        return userConfig;
    }

    public void saveConfigFile(){
        try {
            getConfigFile().save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
