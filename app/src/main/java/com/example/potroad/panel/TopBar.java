package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.R;

/**
 * The top serves as a background for the score and health, at the top of the screen
 */
public class TopBar {
    public static final float TOP_BAR_HEIGHT = 150;

    private final Paint paintTopBar;

    public TopBar(Context context) {
        paintTopBar = new Paint();
        paintTopBar.setColor(ContextCompat.getColor(context, R.color.top_bar));
    }

    public void draw(Canvas canvas, float mapWidth){
        //draw other stuff on roads
        canvas.drawRect(0,0,mapWidth,TOP_BAR_HEIGHT,paintTopBar);
    }
}
