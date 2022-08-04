package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.GameLoop;
import com.example.potroad.R;
import com.example.potroad.object.Player;

public class Score {
    private static final double POINTS_PER_MINUTE = 120.0;
    private static final double POINTS_PER_SECOND = POINTS_PER_MINUTE / 60.0;
    public static final double POINTS_PER_UPDATE = POINTS_PER_SECOND / GameLoop.MAX_UPS;    //if unsure, check measurement units

    private final Player player;

    Paint paint;
    private static final int TEXT_SIZE = 50;
    private static final int POSITION_X = 50;
    private static final int POSITION_Y = 50;

    public Score(Context context, Player player) {
        this.player = player;

        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.score));
        paint.setTextSize(TEXT_SIZE);
    }

    public void draw(Canvas canvas){
        canvas.drawText(String.valueOf((int)player.getGamePoints()),POSITION_X,POSITION_Y,paint);
    }
}
