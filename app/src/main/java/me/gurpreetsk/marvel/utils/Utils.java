package me.gurpreetsk.marvel.utils;

import me.gurpreetsk.marvel.BuildConfig;

/**
 * Created by gurpreet on 14/04/17.
 */

public class Utils {




    public static String getMD5(String timestamp) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            String string = timestamp + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_KEY;
            byte[] array = md.digest(string.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
