package com.marceljurtz.lifecounter;

import android.graphics.Color;

public class ColorService {

    public static final int powerSave = Color.parseColor("#000000");
    public static final int powerSaveTextcolor = Color.parseColor("#CCC2C0");
    public static final int regularTextcolor = Color.parseColor("#161618");

    public static int getDefaultBlack(){
        return black;
    }

    public static int getDefaultBlue(){
        return blue;
    }

    public static int getDefaultGreen(){
        return green;
    }

    public static int getDefaultRed(){
        return red;
    }

    public static int getDefaultWhite(){
        return white;
    }

    public static int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }

    public static String getHexString(int color) {
        return String.format("#%06X", 0xFFFFFF & color);
    }
}