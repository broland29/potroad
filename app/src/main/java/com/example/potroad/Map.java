package com.example.potroad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Map {

    private final float mapWidth;       //width of map/ background/ screen
    private final float mapHeight;      //height of map/ background/ screen
    private final float roadWidth;      //the width of one road

    private final Paint paintRoadOdd;   //color of first, third etc. road
    private final Paint paintRoadEven;  //color of second, fourth etc. road


    public Map(Context context, float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        roadWidth = mapWidth / 5;

        paintRoadOdd = new Paint();
        paintRoadOdd.setColor(ContextCompat.getColor(context,R.color.road_odd));
        paintRoadEven = new Paint();
        paintRoadEven.setColor(ContextCompat.getColor(context,R.color.road_even));

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
        //draw the whole screen then overlap the odd roads
        canvas.drawRect(0,0, mapWidth, mapHeight, paintRoadOdd);
        canvas.drawRect(roadWidth,0,2 * roadWidth, mapHeight, paintRoadEven);
        canvas.drawRect(3 * roadWidth,0,4 * roadWidth, mapHeight, paintRoadEven);
    }
}
