package com.example.potroad.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.potroad.R;


public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect spriteSheetRectangle;
    private final int actualWidth;
    private final int actualHeight;

    public Sprite(SpriteSheet spriteSheet, Rect spriteSheetRectangle, int actualWidth, int actualHeight) {
        this.spriteSheet = spriteSheet;
        this.spriteSheetRectangle = spriteSheetRectangle;
        this.actualWidth = actualWidth;
        this.actualHeight = actualHeight;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                spriteSheetRectangle,
                new Rect(x, y,x + actualWidth, y + actualHeight),
                null);
    }
}
