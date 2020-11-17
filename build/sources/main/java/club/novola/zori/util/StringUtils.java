package club.novola.zori.util;

import java.util.Arrays;
import java.util.Random;

public class StringUtils {
    public static boolean isNumber(String string){
        try{
            Integer.parseInt(string);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public static String toCompleteString(final String[] args, final int start) {
        if(args.length <= start) return "";

        return String.join(" ", Arrays.copyOfRange(args, start, args.length));
    }

    public static String replace(final String string, final String searchChars, String replaceChars) {
        if(string.isEmpty() || searchChars.isEmpty() || searchChars.equals(replaceChars))
            return string;

        if(replaceChars == null)
            replaceChars = "";

        final int stringLength = string.length();
        final int searchCharsLength = searchChars.length();
        final StringBuilder stringBuilder = new StringBuilder(string);

        for(int i = 0; i < stringLength; i++) {
            final int start = stringBuilder.indexOf(searchChars, i);

            if(start == -1) {
                if(i == 0)
                    return string;

                return stringBuilder.toString();
            }

            stringBuilder.replace(start, start + searchCharsLength, replaceChars);
        }

        return stringBuilder.toString();
    }

    public static String randomString(int length, boolean letters, boolean numbers, boolean uppercases) {
        String SALTCHARS = "";
        if (letters && uppercases)
            SALTCHARS += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (letters)
            SALTCHARS += "abcdefghijklmnopqrstuvwxyz";
        if (numbers)
            SALTCHARS += "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
