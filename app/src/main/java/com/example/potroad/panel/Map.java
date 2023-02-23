package com.example.potroad.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.potroad.R;

public class Map {

    private final float mapWidth;       // width of map (background)
    private final float mapHeight;      // height of map
    private final float roadWidth;      // width of one road

    private final Paint paintRoadOdd;   // color of first, third etc. road
    private final Paint paintRoadEven;  // color of second, fourth etc. road

    private final float[] roadMiddle;   // middle x of each road

    public Map(Context context, float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        roadWidth = mapWidth / 5;

        paintRoadOdd = new Paint();
        paintRoadOdd.setColor(ContextCompat.getColor(context, R.color.road_odd));
        paintRoadEven = new Paint();
        paintRoadEven.setColor(ContextCompat.getColor(context,R.color.road_even));

        // road 0 middle: 0.5 width
        // road 1 middle: 1.5 width ...
        roadMiddle = new float[5];
        for (int i = 0; i < 5; i++) {
            roadMiddle[i] = (i + 0.5f) * roadWidth;
        }
    }

    public float[] getRoadMiddle() { return roadMiddle; }

    public float getRoadWidth() {
        return roadWidth;
    }

    //draws the map/ background
    public void draw(Canvas canvas){
        //draw the whole screen then overlap the odd roads
        canvas.drawRect(0,0, mapWidth, mapHeight, paintRoadOdd);
        canvas.drawRect(roadWidth,0,2 * roadWidth, mapHeight, paintRoadEven);
        canvas.drawRect(3 * roadWidth,0,4 * roadWidth, mapHeight, paintRoadEven);
    }
}
