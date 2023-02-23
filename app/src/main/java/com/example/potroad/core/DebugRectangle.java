package com.example.potroad.core;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DebugRectangle {
    // top left coordinates
    private float x;
    private float y;
    // velocity
    private float vx;
    private float vy;
    // dimension
    private float width;
    private float height;
    // color
    private Paint paint;

    public DebugRectangle(float x, float y, float vx, float vy, float width, float height, int color) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = (float) (200 / GameLoop.MAX_UPS);
        this.width = width;
        this.height = height;
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public void update() {
        x += vx;
        y += vy;
    }
}
