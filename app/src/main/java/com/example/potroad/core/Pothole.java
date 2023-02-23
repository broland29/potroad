package com.example.potroad.core;

import android.graphics.Canvas;

import com.example.potroad.graphics.Sprite;

public class Pothole extends Rectangle{

    private double velocity = 200 / GameLoop.MAX_UPS; //pixels per update
    private final Sprite sprite;

    public Pothole(double positionX, double positionY, double width, double height, int color, double currentSpeedup, Sprite sprite) {
        super(positionX, positionY, width, height, color);

        // todo speedup velocity

        this.sprite = sprite;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas,(int)positionX,(int)positionY);
    }

    @Override
    public void update() {
        positionY += velocity;
    }
}
