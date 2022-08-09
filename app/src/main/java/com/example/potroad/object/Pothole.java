package com.example.potroad.object;

import android.graphics.Canvas;

import com.example.potroad.GameLoop;
import com.example.potroad.graphics.Sprite;
import com.example.potroad.graphics.SpriteSheet;

public class Pothole extends Rectangle{

    private static final double INITIAL_SPEED_PIXELS_PER_SECOND = 200;

    private static final double INITIAL_SPAWNS_PER_MINUTE = 30;
    private static final double INITIAL_SPAWNS_PER_SECOND = INITIAL_SPAWNS_PER_MINUTE / 60.0;
    public static final double INITIAL_UPDATES_PER_SPAWN = GameLoop.MAX_UPS / INITIAL_SPAWNS_PER_SECOND;
    public static double updatesUntilNextSpawn; //updates left for next pothole to spawn

    public static final double SPEEDUP = 3;// / GameLoop.MAX_UPS;
    private static final double SPEEDUP_PER_MINUTE = 10.0;
    private static final double SPEEDUP_PER_SECOND = SPEEDUP_PER_MINUTE / 60.0;
    private static final double UPDATES_FOR_SPEEDUP = GameLoop.MAX_UPS / SPEEDUP_PER_SECOND;
    private static double updatesUntilNextSpeedup = UPDATES_FOR_SPEEDUP;

    private static final double INITIAL_Y_VELOCITY = INITIAL_SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS; //pixels per update
    private final Sprite sprite;

    public Pothole(double positionX, double positionY, double width, double height, int color, double currentSpeedup, SpriteSheet spriteSheet) {
        super(positionX, positionY, width, height, color);

        velocityY = INITIAL_Y_VELOCITY + currentSpeedup;

        sprite = spriteSheet.getPotholeSprite(this);
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        //super.draw(canvas,gameDisplay);
        sprite.draw(canvas,(int)positionX,(int)positionY);
    }

    public static boolean readyToSpawn(double currentUpdatesPerSpawn){
        if (updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += currentUpdatesPerSpawn;
            return true; //spawn pothole in update method
        }else{
            updatesUntilNextSpawn--;
            return false;
        }
    }

    public static boolean readyForSpeedup(){
        if (updatesUntilNextSpeedup <= 0){
            updatesUntilNextSpeedup += UPDATES_FOR_SPEEDUP;
            return true; //speed up, in update method
        }else{
            updatesUntilNextSpeedup--;
            return false;
        }
    }

    public void speedUp(){
        //Log.d("Pothole.java","speedUpExistingPothole()");
        velocityY += SPEEDUP;
    }

    @Override
    public void update() {
        //Log.d("Pothole.java","CURRENT VELOCITY : " + velocityY);
        positionY += velocityY;
    }
}
