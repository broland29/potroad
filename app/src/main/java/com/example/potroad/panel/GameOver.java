package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.R;
import com.example.potroad.object.GameDisplay;

public class GameOver {
    private final Paint paint;
    private final String text;
    private static final int TEXT_SIZE = 150;
    private final int positionX;
    private final int positionY;

    public GameOver(Context context, GameDisplay gameDisplay) {
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.game_over));
        paint.setTextSize(TEXT_SIZE);
        text = context.getString(R.string.game_over);

        positionX = gameDisplay.getDisplayWidthPixels()/2 - 370;
        positionY = gameDisplay.getDisplayWidthPixels()/2;
    }

    public void draw(Canvas canvas){
        canvas.drawText(text,positionX,positionY,paint);
    }

}
