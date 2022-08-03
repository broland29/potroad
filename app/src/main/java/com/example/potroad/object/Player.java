package com.example.potroad.object;

import android.graphics.Canvas;

public class Player extends Rectangle{

    int currentRoad = 3;

    public Player(double positionX, double positionY, double width, double height, int color) {
        super(positionX, positionY, width, height, color);
    }

    public void jumpLeft(float roadWidth){
        if (currentRoad > 0){
            positionX -= roadWidth;
            currentRoad--;
        }
    }

    public void jumpRight(float roadWidth){
        if (currentRoad < 5){
            positionX += roadWidth;
            currentRoad++;
        }
    }

    @Override
    public void update() {

    }
}
