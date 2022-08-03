package com.example.potroad.object;

import android.graphics.Rect;

public class GameDisplay {
    public final Rect DISPLAY_RECTANGLE;
    private final int displayWidthPixels;
    private final int displayHeightPixels;

    public GameDisplay(int displayWidthPixels, int displayHeightPixels) {
        this.displayWidthPixels = displayWidthPixels;
        this.displayHeightPixels = displayHeightPixels;

        DISPLAY_RECTANGLE = new Rect(0,0,displayWidthPixels,displayHeightPixels);
    }

    public void update(){

    }

    public int getDisplayWidthPixels() {
        return displayWidthPixels;
    }
}
