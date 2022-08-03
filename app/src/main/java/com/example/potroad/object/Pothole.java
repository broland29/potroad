package com.example.potroad.object;

import android.graphics.Canvas;

import com.example.potroad.GameLoop;

public class Pothole extends Rectangle{

    private static final double SPEED_PIXELS_PER_SECOND = 200;

    private static final double SPAWNS_PER_MINUTE = 30;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    public Pothole(double positionX, double positionY, double width, double height, int color) {
        super(positionX, positionY, width, height, color);
        velocityY = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels per update
    }

    public static boolean readyToSpawn(){
        if (updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true; //spawn pothole in update method
        }else{
            updatesUntilNextSpawn--;
            return false;
        }
    }

    @Override
    public void update() {
        positionY += velocityY;
    }
}
