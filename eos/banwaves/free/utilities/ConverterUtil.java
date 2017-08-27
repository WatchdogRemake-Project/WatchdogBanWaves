package eos.banwaves.free.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

public class ConverterUtil {

    public static String getNameFromUUID(String uuid) {
        String name = null;
        try {
            URL url = new URL("http://uuid.turt2live.com/api/v2/name/" + uuid.replaceAll("-", ""));
            Scanner jsonScanner = new Scanner(url.openConnection().getInputStream(), "UTF-8");
            String json = jsonScanner.next();
            name = (((JSONObject) new JSONParser().parse(json)).get("name")).toString();
            jsonScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getUUIDFromName(String name, boolean onlinemode) {
        return getUUIDFromName(name, onlinemode, false);
    }

    public static String getUUIDFromName(String name, boolean onlinemode, boolean withSeperators) {
        String uuid = null;
        if (onlinemode) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://uuid.turt2live.com/api/v2/uuid/" + name).openStream()));
                uuid = (((JSONObject) new JSONParser().parse(in)).get("uuid")).toString().replaceAll("\"", "");
                in.close();
            } catch (Exception e) {
                System.out.println("Unable to get UUID for: " + name + "! Giving offline UUID!");
                //e.printStackTrace();
                uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)).toString();
            }
        } else {
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)).toString();
        }
        if (uuid != null) {
            if (withSeperators) {
                if (!uuid.contains("-")) {
                    return uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
                }
            } else {
                uuid = uuid.replaceAll("-", "");
            }
        }
        return uuid;
    }


}
