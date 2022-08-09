package com.example.potroad.object;

import android.app.Activity;
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

import com.example.potroad.GameLoop;
import com.example.potroad.activity.GameOverActivity;
import com.example.potroad.Map;
import com.example.potroad.R;
import com.example.potroad.graphics.PlayerAnimator;
import com.example.potroad.graphics.SpriteSheet;
import com.example.potroad.panel.HealthBar;
import com.example.potroad.panel.Score;
import com.example.potroad.panel.TopBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//SurfaceView - "Provides a dedicated drawing surface embedded inside of a view hierarchy"
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final GameDisplay gameDisplay;
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
    private final double PLAYER_SIZE;
    private final double POTHOLE_SIZE;

    private final SpriteSheet spriteSheet;

    public Game(Context context) {
        super(context);
        this.context = context;

        //TODO: what is this?
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this,surfaceHolder);

        //initialize gameDisplay (only once)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels,displayMetrics.heightPixels);

        float mapWidth = displayMetrics.widthPixels;
        float mapHeight = displayMetrics.heightPixels;
        map = new Map(context,mapWidth,mapHeight);
        ROAD_SIZE = mapWidth / 5;
        PLAYER_SIZE = ROAD_SIZE * 0.70;
        POTHOLE_SIZE = ROAD_SIZE * 0.40;

        spriteSheet = new SpriteSheet(context);

        player = new Player(
                map.getMiddleWidthOfRoad(3) - PLAYER_SIZE / 2,
                displayMetrics.heightPixels - 2 * PLAYER_SIZE,//offset of player size from bottom of screen
                PLAYER_SIZE,
                PLAYER_SIZE,
                ContextCompat.getColor(context, R.color.player),
                spriteSheet);

        potholeList = new ArrayList<>();

        currentPotholeSpeedBonus = 0;
        currentUpdatesPerPotholeSpawn = Pothole.INITIAL_UPDATES_PER_SPAWN;

        topBar = new TopBar(context);
        score = new Score(context,player);
        healthBar = new HealthBar(context,spriteSheet.getHealthSprite());
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.draw(canvas);

        player.draw(canvas, gameDisplay);

        for(Pothole pothole : potholeList){
            pothole.draw(canvas,gameDisplay);
        }

        topBar.draw(canvas,gameDisplay.getDisplayWidthPixels());    //top bar's draw after potholes, so they can spawn behind it seamlessly
        score.draw(canvas);
        healthBar.draw(canvas, player.getHealthPoints(), gameDisplay);

        if(player.getHealthPoints() <= 0){
            //black magic: https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
            Intent intent = new Intent(context, GameOverActivity.class);
            intent.putExtra("score",(int) player.getGamePoints());    //pass high score
            context.startActivity(intent);
        }

    }

    public void update(){

        if(player.getHealthPoints() <= 0){
            return;
        }

        player.update();

        if(Pothole.readyToSpawn(currentUpdatesPerPotholeSpawn)){
            int i = (int)((Math.random() * 5) + 1);
            potholeList.add(new Pothole(
                    map.getMiddleWidthOfRoad(i) - POTHOLE_SIZE / 2,
                    -50,
                    POTHOLE_SIZE,
                    POTHOLE_SIZE,
                    ContextCompat.getColor(getContext(),R.color.pothole),
                    currentPotholeSpeedBonus,
                    spriteSheet));
        }

        if(Pothole.readyForSpeedup()){
            currentPotholeSpeedBonus += Pothole.SPEEDUP;  //for future potholes' starting speed
            for(Pothole pothole : potholeList){
                pothole.speedUp();
            }
            Pothole.updatesUntilNextSpawn = currentUpdatesPerPotholeSpawn; // a pothole spawned, but the next one will spawn faster
            currentUpdatesPerPotholeSpawn *= 0.8;
            Log.d("Game.java","update() - currentUpdatesPerPotholeSpawn = " + currentUpdatesPerPotholeSpawn);
        }

        Iterator<Pothole> potholeIterator = potholeList.iterator();
        while (potholeIterator.hasNext()){
            Rectangle pothole = potholeIterator.next();
            if (Rectangle.isColliding(pothole,player)){
                System.out.println("Collide");
                player.setHealthPoints(player.getHealthPoints() - 1);
                potholeIterator.remove();
            }
            if (pothole.getPositionY() > gameDisplay.getDisplayHeightPixels()){
                System.out.println("Left screen");
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
            if (event.getX() < gameDisplay.getDisplayWidthPixels() / 2.0) {
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
