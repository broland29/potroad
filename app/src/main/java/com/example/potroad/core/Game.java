package com.example.potroad.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.potroad.activity.GameOverActivity;
import com.example.potroad.panel.Map;
import com.example.potroad.R;
import com.example.potroad.graphics.SpriteSheet;
import com.example.potroad.panel.HealthBar;
import com.example.potroad.panel.Score;
import com.example.potroad.panel.TopBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// "SurfaceView provides a dedicated drawing surface embedded inside of a view hierarchy"
// "SurfaceHolder allows you to edit the pixels in the surface, monitor changes to the surface..."
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    //private final GameDisplay gameDisplay;
    private GameLoop gameLoop;
    private final Map map;

    private final Player player;
    private final List<Pothole> potholeList;

    private final TopBar topBar;
    private final Score score;
    private final HealthBar healthBar;

    private final Context context;

    private double currentPotholeSpeedBonus;
    private double currentUpdatesPerPotholeSpawn;

    private final double ROAD_SIZE;
    private final double POTHOLE_SIZE;

    private final SpriteSheet spriteSheet;

    private final float width;
    private final float height;


    public Game(Context context) {
        super(context);
        this.context = context;

        // needed for starting the game through surfaceCreated
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this,surfaceHolder);

        // getting width and height of display
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        // draw background
        map = new Map(context, width, height);

        // initialize sprite sheet
        spriteSheet = new SpriteSheet(context);

        // establish some constants
        ROAD_SIZE = width / 5;
        double playerSize = ROAD_SIZE * 0.70;
        POTHOLE_SIZE = ROAD_SIZE * 0.40;

        // initialize player
        player = new Player(
                map.middleOfRoad(3) - playerSize / 2,   // to the left by half size
                displayMetrics.heightPixels - 2 * playerSize,           // up by twice the size
                playerSize,
                playerSize,
                ContextCompat.getColor(context, R.color.player),
                spriteSheet);

        // deal with potholes
        potholeList = new ArrayList<>();

        currentPotholeSpeedBonus = 0;
        currentUpdatesPerPotholeSpawn = Pothole.INITIAL_UPDATES_PER_SPAWN;

        // other static elements
        topBar = new TopBar(context);
        score = new Score(context);
        healthBar = new HealthBar(context, spriteSheet.getHealthSprite());
    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.draw(canvas);
        player.draw(canvas);

        for(Pothole pothole : potholeList){
            pothole.draw(canvas);
        }

        topBar.draw(canvas, width);    //top bar's draw after potholes, before score and healthBar!
        score.draw(canvas, player.getGamePoints());
        healthBar.draw(canvas, player.getHealthPoints(), width);

        if(player.getHealthPoints() <= 0){
            // "An intent is an abstract description of an operation to be performed"
            Intent intent = new Intent(context, GameOverActivity.class);
            intent.putExtra("score",(int) player.getGamePoints());  // pass high score
            context.startActivity(intent);                                // execute intent
        }
    }


    public void update(){

        // update player //
        player.update();

        // update potholes //
        // spawn pothole if necessary
        if(Pothole.readyToSpawn(currentUpdatesPerPotholeSpawn)){
            int roadNo = (int) ((Math.random() * 5) + 1);
            potholeList.add(new Pothole(
                    map.middleOfRoad(roadNo) - POTHOLE_SIZE / 2,
                    -50,
                    POTHOLE_SIZE,
                    POTHOLE_SIZE,
                    ContextCompat.getColor(context, R.color.pothole),
                    currentPotholeSpeedBonus,
                    spriteSheet.getPotholeSprite((int) POTHOLE_SIZE, (int) POTHOLE_SIZE)));
        }

        // speed up potholes if necessary
        if(Pothole.readyForSpeedup()){
            currentPotholeSpeedBonus += Pothole.SPEEDUP;  //for future potholes' starting speed
            for(Pothole pothole : potholeList){
                pothole.speedUp();
            }
            Pothole.updatesUntilNextSpawn = currentUpdatesPerPotholeSpawn; // a pothole spawned, but the next one will spawn faster
            currentUpdatesPerPotholeSpawn *= 0.8;
            Log.d("Game.java","update() - currentUpdatesPerPotholeSpawn = " + currentUpdatesPerPotholeSpawn);
        }

        // iterate potholes
        Iterator<Pothole> potholeIterator = potholeList.iterator();
        while (potholeIterator.hasNext()){
            Rectangle pothole = potholeIterator.next();

            // check collision with player
            if (Rectangle.isColliding(pothole, player)){
                System.out.println("Collision!");
                player.setHealthPoints(player.getHealthPoints() - 1);
                potholeIterator.remove();
            }

            // check if pothole out-of-screen
            if (pothole.getPositionY() > height){
                System.out.println("Pothole out of screen");
                potholeIterator.remove();
            }
        }

        for(Pothole pothole : potholeList){
            pothole.update();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //when thread terminated, need a new one (threads can be ran once)
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, surfaceHolder);
        }

        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() < width / 2.0) {
                System.out.println("left");
                player.jumpLeft(map.getRoadWidth());
            } else {
                System.out.println("right");
                player.jumpRight(map.getRoadWidth());
            }
            return true; //event handled
        }
        return false;
    }

    public void pause(){
        gameLoop.stopLoop();
    }
}
