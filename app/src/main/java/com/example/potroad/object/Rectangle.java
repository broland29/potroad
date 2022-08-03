package com.example.potroad.object;

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

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        canvas.drawRect(
                (float) positionX,              //left
                (float) positionY,              //top
                (float) (positionX + width),    //right
                (float) (positionY + height),   //bottom
                paint
        );
    }
}
