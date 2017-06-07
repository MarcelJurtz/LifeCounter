package com.marceljurtz.lifecounter;

import android.graphics.Color;

public class ColorService {
    public static final int black = Color.parseColor("#CCC2C0");
    public static final int blue = Color.parseColor("#AAE0FA");
    public static final int green = Color.parseColor("#9BD3AE");
    public static final int red = Color.parseColor("#FAAA8F");
    public static final int white = Color.parseColor("#FFFCD6");
    public static final int powerSafe = Color.parseColor("#000000");
    public static final int powerSafeTextcolor = Color.parseColor("#CCC2C0");
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
}