package eos.banwaves.free.utilities;


import eos.banwaves.free.BanWaves;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ServerUtil {

    private static Player[] acquirePlayers() {
        return acquireServer().getOnlinePlayers().toArray(new Player[acquireServer().getOnlinePlayers().size()]);
    }

    private static Server acquireServer() {
        return Bukkit.getServer();
    }

    @SuppressWarnings("unused")
	public static void broadcast(String message) {
        Player[] arrayofPlayers;
        int x = (arrayofPlayers = acquirePlayers()).length;
        for (int i = 0; i < x; i++) {
            Player cur = arrayofPlayers[i];
            PlayerUtil.sendMessage(ColorUtil.translateColor(message));
        }
    }

    public static void consoleColorSend(String text) {
        if (text == null) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.translateColor("&cUnable to process message. Text is invalid"));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(ColorUtil.translateColor(text));
    }

    public static void broadcast(String sender, String message) {
        broadcast(ColorUtil.translateColor("&f&l" + sender + " " + "&b" + message));
    }


    public static void consoleDelay(String message, int seconds){
        Bukkit.getScheduler().scheduleSyncDelayedTask(BanWaves.getInstance(), () -> consoleColorSend(message), 20L * seconds);
    }

    public static void broadcastMagic(String message) {
        broadcast(ColorUtil.translateColor("&2&k" + message));
    }

    public static double getFilledPercent() {
        return acquirePlayers().length / acquireServer().getMaxPlayers();
    }

    //  public static void staffOnlyBroadcast(String message){
//        core.redisFactory.publish(RedisConfig.CHANNEL3, "staff:" + UtilServer.translateColor(message));
    // }

    public static void out(String message) {
        System.out.println(message);
    }

    public static void log(String method, String type, String by) {
        consoleColorSend("&e[Log-Bot]- Daily Log &d`" + type + "` &dMethod/&a" + method + " executed by: " + by + " #" + MathUtil.genReceipt());
    }

    public static String getServerIP(){
        return Bukkit.getIp();
    }

    public static int getServerPort(){
        return Bukkit.getPort();
    }

    public static int getMaxPlayers(){
        return Bukkit.getMaxPlayers();
    }

    public static int getAmountPlayersOnline(){
        return Bukkit.getOnlinePlayers().size();
    }
}
