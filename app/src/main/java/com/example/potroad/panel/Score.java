package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.example.potroad.core.GameLoop;
import com.example.potroad.R;

public class Score {
    private static final double POINTS_PER_MINUTE = 120.0;
    private static final double POINTS_PER_SECOND = POINTS_PER_MINUTE / 60.0;
    public static final double POINTS_PER_UPDATE = POINTS_PER_SECOND / GameLoop.MAX_UPS;    //if unsure, check measurement units

    Paint paint;
    private static final int TEXT_SIZE = 100;
    private static final int POSITION_X = 50;
    private static final int POSITION_Y = 110;

    public Score(Context context) {
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.score));
        paint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));

        paint.setTextSize(TEXT_SIZE);
    }

    public void draw(Canvas canvas, double gamePoints){
        canvas.drawText(String.valueOf((int) gamePoints), POSITION_X, POSITION_Y, paint);
    }
}
