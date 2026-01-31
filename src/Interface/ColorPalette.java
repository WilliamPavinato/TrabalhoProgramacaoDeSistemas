package Interface;

import java.awt.Color;

public enum ColorPalette {
    TITLE(new Color(255, 217, 102)), // also used for blocking
    BG(new Color(24, 25, 26)),
    BG_GRID(new Color(36, 37, 38)),
    GRID(new Color(58, 59, 60)), // also used for the buttons
    TEXT(new Color(228, 230, 235));


    private final Color color;

    private ColorPalette(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}