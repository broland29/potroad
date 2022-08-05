package com.example.potroad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.potroad.R;
import com.example.potroad.object.Game;

public class GameActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Context - "Interface to global information about an application environment"
        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        game.pause();   //avoids crash ex: if back button is pressed
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //TODO: make sure that activities are closed, there are not multiple activities of same type running
    //https://developer.android.com/guide/components/activities/tasks-and-back-stack
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameActivity.java","onDestroy()");
    }

    @Override
    public void onBackPressed() {
        //disabled back button
        //super.onBackPressed();
    }

    public void showGameOver(){
        startActivity(new Intent(this, GameActivity.class));
    }
}