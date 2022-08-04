package com.example.potroad.object;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.potroad.GameLoop;
import com.example.potroad.Map;
import com.example.potroad.R;
import com.example.potroad.panel.GameOver;
import com.example.potroad.panel.HealthBar;
import com.example.potroad.panel.Score;

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

    private final Score score;
    private final GameOver gameOver;
    private final HealthBar healthBar;

    public Game(Context context) {
        super(context);

        //TODO: what is this?
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this,surfaceHolder);

        //initialize gameDisplay (only once)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels,displayMetrics.heightPixels);

        map = new Map(context,displayMetrics.widthPixels,displayMetrics.heightPixels);

        player = new Player(
                map.getMiddleWidthOfRoad(3) - 25,
                displayMetrics.heightPixels - 300,
                50,
                50,
                ContextCompat.getColor(context, R.color.player));

        potholeList = new ArrayList<>();
        for(int i=0; i<5; i++){
            potholeList.add(new Pothole(
                    map.getMiddleWidthOfRoad(i + 1) - 25,
                    i * 200,
                    50,
                    50,
                    ContextCompat.getColor(context,R.color.pothole)));
        }

        score = new Score(context,player);
        gameOver = new GameOver(context,gameDisplay);
        healthBar = new HealthBar(context);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.draw(canvas);

        player.draw(canvas, gameDisplay);

        for(Pothole pothole : potholeList){
            pothole.draw(canvas,gameDisplay);
        }

        score.draw(canvas);
        healthBar.draw(canvas, player.getHealthPoints(), gameDisplay);

        if(player.getHealthPoints() <= 0){
            gameOver.draw(canvas);
        }
    }

    public void update(){

        if(player.getHealthPoints() <= 0){
            return;
        }

        player.update();

        if(Pothole.readyToSpawn()){
            int i = (int)((Math.random() * 4) + 1);
            potholeList.add(new Pothole(
                    map.getMiddleWidthOfRoad(i + 1) - 25,
                    100,
                    50,
                    50,
                    ContextCompat.getColor(getContext(),R.color.pothole)));
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
