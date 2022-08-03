package com.example.potroad;

import android.content.Context;
import android.view.SurfaceView;

import com.example.potroad.object.Player;
import com.example.potroad.object.Pothole;
import com.example.potroad.object.Road;

import java.util.List;

//SurfaceView - "Provides a dedicated drawing surface embedded inside of a view hierarchy"
public class Game extends SurfaceView {

    private Player player;
    private List<Road> roadList;
    private List<Pothole> potholeList;

    public Game(Context context) {
        super(context);
    }

    public void update(){

    }

    public void draw(){

    }
}
