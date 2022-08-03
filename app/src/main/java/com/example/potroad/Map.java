package com.example.potroad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Map {

    //width and height of map/ background
    private final float width;
    private final float height;

    //roads will have two colors
    private final Paint paint_1;
    private final Paint paint_2;

    //the width of one road
    private final float roadWidth;

    public Map(Context context, float width, float height) {
        this.width = width;
        this.height = height;

        roadWidth = width / 5;

        paint_1 = new Paint();
        paint_1.setColor(ContextCompat.getColor(context,R.color.road_1));
        paint_2 = new Paint();
        paint_2.setColor(ContextCompat.getColor(context,R.color.road_2));
    }

    //since first road is at half width, second is at 1.5 width...
    public float getMiddleWidthOfRoad(int roadNo){
        return roadWidth * (roadNo - 0.5f);
    }

    public float getRoadWidth() {
        return roadWidth;
    }

    //draws the map/ background
    public void draw(Canvas canvas){
        canvas.drawRect(0,0,width,height,paint_1);

        canvas.drawRect(roadWidth,0,2 * roadWidth,height,paint_2);
        canvas.drawRect(3 * roadWidth,0,4 * roadWidth,height,paint_2);
    }
}
