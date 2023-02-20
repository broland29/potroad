package com.example.potroad.panel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.R;
import com.example.potroad.graphics.Sprite;

public class HealthBar {
    private final Paint healthPaint;
    public static final int ICON_SIZE = 100;
    public static final int PADDING_X = 30;
    public static final int PADDING_Y = 25;

    private final Sprite sprite;

    public HealthBar(Context context, Sprite sprite) {
        healthPaint = new Paint();
        healthPaint.setColor(ContextCompat.getColor(context, R.color.health));

        this.sprite = sprite;
    }

    //TODO: maybe can avoid drawing in every frame
    public void draw(Canvas canvas, int healthPoints, float width){
        int healthBarWidth = healthPoints * (ICON_SIZE + PADDING_X) + PADDING_X;  //ex: padding on each health's left + last one's right

        int healthBarLeft = (int)width - healthBarWidth;

        for(int i=0; i<healthPoints; i++){
            sprite.draw(
                    canvas,
                    healthBarLeft + i * ICON_SIZE + (i + 1) * PADDING_X,
                    PADDING_Y
                    );
//            canvas.drawRect(
//                    healthBarLeft + i * ICON_SIZE + (i + 1) * PADDING_X,
//                    PADDING_Y,
//                    healthBarLeft + (i + 1) * ICON_SIZE + (i + 1) * PADDING_X,   //just add ICON_SIZE
//                    PADDING_Y + ICON_SIZE,
//                    healthPaint
//            );
        }
    }
}
