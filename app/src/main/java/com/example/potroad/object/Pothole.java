package com.example.potroad.object;

import android.graphics.Canvas;

import com.example.potroad.GameLoop;

public class Pothole extends Rectangle{

    private static final double SPEED_PIXELS_PER_SECOND = 200;

    public Pothole(double positionX, double positionY, double width, double height, int color) {
        super(positionX, positionY, width, height, color);
        velocityY = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels per update
    }

    @Override
    public void update() {
        positionY += velocityY;
    }
}
