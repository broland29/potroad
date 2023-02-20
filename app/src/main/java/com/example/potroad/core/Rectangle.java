package com.example.potroad.core;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Rectangle extends GameObject{
    protected double width;
    protected double height;
    protected Paint paint;

    public Rectangle(double positionX, double positionY, double width, double height, int color){
        super(positionX,positionY);

        this.width = width;
        this.height = height;

        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas){
        canvas.drawRect(
                (float) positionX,              //left
                (float) positionY,              //top
                (float) (positionX + width),    //right
                (float) (positionY + height),   //bottom
                paint
        );
    }

    //https://www.youtube.com/watch?v=_MyPLZSGS3s
    //can think of it with hal planes, divide logic on axes
    public static boolean isColliding(Rectangle r1, Rectangle r2){
        return r1.getPositionX() + r1.getWidth() >= r2.getPositionX() &&        //top of r1 is <= bottom of r2
                r1.getPositionX() <= r2.getPositionX() + r2.getWidth() &&       //right side of r1 is >= left side of r2 and
                r1.getPositionY() + r1.getHeight() >= r2.getPositionY() &&      //left side of r1 is <= right side of r2 and
                r1.getPositionY() <= r2.getPositionY() + r2.getHeight();        //bottom of r1 is >= top of r2 and
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
