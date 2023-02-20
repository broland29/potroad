package com.example.potroad.core;

import android.graphics.Canvas;

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double directionX;
    protected double directionY;

    public GameObject(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();


    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
