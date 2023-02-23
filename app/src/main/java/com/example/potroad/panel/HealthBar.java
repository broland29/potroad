package com.example.potroad.panel;

import android.graphics.Canvas;

import com.example.potroad.graphics.Sprite;

//TODO: maybe can avoid drawing in every frame

/** Health icons on the top right of the screen */
public class HealthBar {
    public static final int ICON_SIZE = 100;
    public static final int PADDING_X = 30;
    public static final int PADDING_Y = 25;

    private final Sprite sprite;

    public HealthBar(Sprite sprite) {
        this.sprite = sprite;
    }

    // draw health points, from right to left
    public void draw(Canvas canvas, int healthPoints, int width) {

        // the current amount of offset (to the left from the right edge)
        int offset = PADDING_X + ICON_SIZE;

        for(int i = 0; i < healthPoints; i++){
            sprite.draw(canvas, width - offset, PADDING_Y); // draw the health sprite
            offset += ICON_SIZE + PADDING_X;                   // update the offset for the next one
        }
    }
}
