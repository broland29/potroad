package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.R;
import com.example.potroad.object.GameDisplay;

public class HealthBar {
    private final Paint healthPaint;
    private final Paint healthBarPaint;
    private static final int ICON_SIZE = 70;
    private static final int PADDING_X = 20;
    private static final int PADDING_Y = 20;


    public HealthBar(Context context) {
        healthPaint = new Paint();
        healthPaint.setColor(ContextCompat.getColor(context, R.color.health));
        healthBarPaint = new Paint();
        healthBarPaint.setColor(ContextCompat.getColor(context, R.color.health_bar));
    }

    //TODO: maybe can avoid drawing in every frame
    public void draw(Canvas canvas, int healthPoints, GameDisplay gameDisplay){
        int healthBarWidth = healthPoints * (ICON_SIZE + PADDING_X) + PADDING_X;  //ex: padding on each health's left + last one's right
        int healthBarHeight = ICON_SIZE + 2 * PADDING_Y;

        int healthBarLeft = gameDisplay.getDisplayWidthPixels() - healthBarWidth;
        int healthBarTop = 0;

        canvas.drawRect(
                healthBarLeft,
                healthBarTop,
                healthBarLeft + healthBarWidth,                        //right
                healthBarTop + healthBarHeight,
                healthBarPaint
        );

        for(int i=0; i<healthPoints; i++){
            canvas.drawRect(
                    healthBarLeft + i * ICON_SIZE + (i + 1) * PADDING_X,
                    PADDING_Y,
                    healthBarLeft + (i + 1) * ICON_SIZE + (i + 1) * PADDING_X,   //just add ICON_SIZE
                    PADDING_Y + ICON_SIZE,
                    healthPaint
            );
        }
    }


}
