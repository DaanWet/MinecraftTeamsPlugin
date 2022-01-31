package me.damascus2000.minecraftsurvivalteamsplugin.utils;

public class Utils {

    public static boolean isInt(String num){
        if (num == null) {
            return false;
        }
        try {
            int d = Integer.parseUnsignedInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
