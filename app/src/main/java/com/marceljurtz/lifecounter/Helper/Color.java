package com.marceljurtz.lifecounter.Helper;

public class Color {

    // Default colors for magic cards
    public static final int DEFAULT_BLACK = android.graphics.Color.parseColor("#CCC2C0");
    public static final int DEFAULT_BLUE = android.graphics.Color.parseColor("#AAE0FA");
    public static final int DEFAULT_GREEN = android.graphics.Color.parseColor("#9BD3AE");
    public static final int DEFAULT_RED = android.graphics.Color.parseColor("#FAAA8F");
    public static final int DEFAULT_WHITE = android.graphics.Color.parseColor("#FFFCD6");

    public static final int color_save = android.graphics.Color.parseColor("#000000");

    private static final String shared_preferences_color_black = "COLOR_BLACK";
    private static final String shared_preferences_color_blue = "COLOR_BLUE";
    private static final String shared_preferences_color_green = "COLOR_GREEN";
    private static final String shared_preferences_color_red = "COLOR_RED";
    private static final String shared_preferences_color_white = "COLOR_WHITE";

    private int intValue;
    private String prefString;

    public Color(MagicColor baseColor, int intValue) {
        this.intValue = intValue;
        switch(baseColor) {
            case BLUE:
                prefString = shared_preferences_color_blue;
                break;
            case GREEN:
                prefString = shared_preferences_color_green;
                break;
            case RED:
                prefString = shared_preferences_color_red;
                break;
            case WHITE:
                prefString = shared_preferences_color_white;
                break;
            default:
                prefString = shared_preferences_color_black;
        }
    }

    public static Color getDefaultColor(MagicColor color) {
        switch(color) {
            case BLUE:
                return new Color(MagicColor.BLUE, DEFAULT_BLUE);
            case GREEN:
                return new Color(MagicColor.GREEN, DEFAULT_GREEN);
            case RED:
                return new Color(MagicColor.RED, DEFAULT_RED);
            case WHITE:
                return new Color(MagicColor.WHITE, DEFAULT_WHITE);
            default:
                return new Color(MagicColor.BLACK, DEFAULT_BLACK);
        }
    }
}
