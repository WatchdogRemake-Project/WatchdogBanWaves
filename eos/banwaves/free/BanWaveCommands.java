package eos.banwaves.free;

import eos.banwaves.free.libraries.command.Command;
import eos.banwaves.free.libraries.command.CommandArgs;
import eos.banwaves.free.libraries.command.CommandFramework;
import eos.banwaves.free.libraries.dconfig.DConfig;
import eos.banwaves.free.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BanWaveCommands {

    CommandFramework framework;

    public BanWaveCommands() {
        framework = new CommandFramework(BanWaves.getInstance());
        framework.registerCommands(this);
    }

    public static String INVALID_PARAMETERS = ColorUtil.translateColor("&f[WATCHDOG] &7Invalid &c&n&lPARAMETERS!");
    public static String INVALID_USER = ColorUtil.translateColor("&f[WATCHDOG] &7Invalid &c&n&lUSER!");

    @Command(name = "banwave", description = "BanWaves base command")
    public void banWave(CommandArgs args) {
        Player pl = args.getPlayer();
        pl.sendMessage("§f[WATCHDOG] §cBanWaves made by §eEos, ItzSomebody & NulledXenforo");
    }

    @SuppressWarnings("deprecation")
	@Command(name = "banwave.add", permission = "banwave.add", description = "Banwaves add command")
    public void banWaveAdd(CommandArgs args) {
        Player pl = args.getPlayer();

        if (args.length() >= 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args.getArgs(0));
            DConfig pending = new DConfig("pendingusers");

            StringBuilder str = new StringBuilder();
            String reason;
            for (int i = 1; i < args.length(); i++) {
                str.append(args.getArgs(i)).append(" ");
            }
            reason = str.toString();
            if (reason.equalsIgnoreCase(null) || reason.isEmpty() || reason.equals(" ")) {
                reason = "No Reason Specified";
            } else {
                reason = str.toString();
            }

            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Name", target.getName());
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".UUID", String.valueOf(target.getUniqueId()));
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Date", String.valueOf(Calendar.getInstance().getTime()));
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".Reason", reason);
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".ExecutedBy", args.getSender().getName());
            pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", args.getSender().getName());
            if (target.isOnline()) {
                pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", true);
            } else {
                pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()) + ".wasOnline", false);
            }

            pending.saveConfigFile();

            PlayerUtil.sendMessage(pl, BanWaves.getInstance().getConfig().getString("BanWaves.CommandsMessages.BanWaveAddCommand").replace("{name}", target.getName()));

        } else {
            PlayerUtil.sendMessage(pl, INVALID_PARAMETERS);
        }
    }

    @SuppressWarnings("deprecation")
	@Command(name = "banwave.remove", permission = "banwave.remove", description = "Banwaves remove command")
    public void banWaveRemove(CommandArgs args) {
        Player pl = args.getPlayer();
        DConfig pending = new DConfig("pendingusers");

        if (args.length() == 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args.getArgs(0));
            List<String> pendingusers = new ArrayList<>();
            for ( String s : pending.getConfigFile().getConfigurationSection("PendingUsers").getKeys(false) ) {
                pendingusers.add(s);
            }
            if ( pendingusers.contains(String.valueOf(target.getUniqueId())) ) {
                pending.getConfigFile().set("PendingUsers." + String.valueOf(target.getUniqueId()), null);
                pending.saveConfigFile();
                PlayerUtil.sendMessage(pl, BanWaves.getInstance().getConfig().getString("BanWaves.CommandsMessages.BanWaveRemoveCommand").replace("{name}", target.getName()));
            } else {
                PlayerUtil.sendMessage(pl, INVALID_USER);
            }
        } else {
            PlayerUtil.sendMessage(pl, INVALID_PARAMETERS);
        }
    }

    @Command(name = "banwave.on", permission = "banwave.on", description = "Banwaves on command")
    public void banWaveOn(CommandArgs args) {
        Player pl = args.getPlayer();

        if ( BanWaves.getInstance().getConfig().getBoolean("BanWaves.EnableBanWave") ) {
            PlayerUtil.sendMessage(pl, "&f[WATCHDOG] &7BAN WAVE IS ALREADY ENABLED!");
        } else {
            BanWaves.getInstance().getConfig().set("BanWaves.EnableBanWave", true);
            PlayerUtil.sendMessage(pl, BanWaves.getInstance().getConfig().getString("BanWaves.CommandsMessages.BanWaveOnCommand"));
            BanWaves.getInstance().saveConfig();
        }
    }

    @Command(name = "banwave.off", permission = "banwave.off", description = "Banwaves off command")
    public void banWaveOff(CommandArgs args) {
        Player pl = args.getPlayer();
        if ( !BanWaves.getInstance().getConfig().getBoolean("BanWaves.EnableBanWave") ) {
            PlayerUtil.sendMessage(pl, "&f[WATCHDOG] &7BAN WAVE IS ALREADY DISABLED!");
        } else {
            BanWaves.getInstance().getConfig().set("BanWaves.EnableBanWave", false);
            PlayerUtil.sendMessage(pl, BanWaves.getInstance().getConfig().getString("BanWaves.CommandsMessages.BanWaveOffCommand"));

            if ( BanWaves.getInstance().getConfig().getBoolean("BanWaves.EraseBansOnDisable") ) {
                DConfig pending = new DConfig("pendingusers");
                pending.getConfigFile().set("PendingUsers", null);
                pending.saveConfigFile();
            }
            BanWaves.getInstance().saveConfig();
        }
    }
    @Command(name = "banwave.execute", permission = "banwave.execute", description = "Banwaves execute command")
    public void banWaveExecute(CommandArgs args) {
        Player pl = args.getPlayer();
        if ( BanWaveUtil.getAmountToBan() == 0 ) {
            PlayerUtil.sendMessage(pl, "&f[WATCHDOG] &7NO USERS TO BAN!");
            return;
        }
        PlayerUtil.sendMessage(pl, BanWaves.getInstance().getConfig().getString("BanWaves.CommandsMessages.BanWaveExecuteCommand"));
        BanWaveUtil.executeBanWave();
    }

    @Command(name = "banwave.reload", permission = "banwave.reload", description = "Banwaves reload command")
    public void banWaveReload(CommandArgs args) {
        Player pl = args.getPlayer();
        PlayerUtil.sendMessage(pl, "&7Reloaded Banwaves config | &7Developer &cEos &7& &cItzSomebody &7@ &cspigotmc.org");
        BanWaves.getInstance().reloadConfig();
    }
}
