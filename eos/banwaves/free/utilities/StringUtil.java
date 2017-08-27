package eos.banwaves.free.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zeljko on 4/23/2016.
 */
public class StringUtil {

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPresent(String haystack, String needle) {
        if (needle.equals(""))
            return true;
        if (haystack == null || haystack.equals(""))
            return false;

        Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
        Matcher m = p.matcher(haystack);
        return m.find();
    }


    public static boolean equalTo(String string, String string2){
        return string.equalsIgnoreCase(string2);
    }

}
