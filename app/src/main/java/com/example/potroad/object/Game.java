package com.example.potroad.object;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.potroad.GameLoop;
import com.example.potroad.Map;
import com.example.potroad.R;

import java.util.ArrayList;
import java.util.List;

//SurfaceView - "Provides a dedicated drawing surface embedded inside of a view hierarchy"
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final GameDisplay gameDisplay;
    private GameLoop gameLoop;
    private final Map map;

    private final Player player;
    private final List<Pothole> potholeList;

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
        potholeList.add(new Pothole(400,400,50,50,Color.BLUE));
        potholeList.add(new Pothole(600,400,50,50,Color.BLUE));
        potholeList.add(new Pothole(800,400,50,50,Color.BLUE));
        potholeList.add(new Pothole(400,600,50,50,Color.BLUE));
        potholeList.add(new Pothole(400,800,50,50,Color.BLUE));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.draw(canvas);

        player.draw(canvas, gameDisplay);

        for(Pothole pothole : potholeList){
            pothole.draw(canvas,gameDisplay);
        }
    }

    public void update(){
        player.update();
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
}