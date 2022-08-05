package com.example.potroad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.potroad.R;
import com.example.potroad.object.Game;

/**
 * The activity which contains the game itself (active when the user is playing)
 *  - launched from MainActivity or GameOverActivity
 *  - brings to GameOverActivity
 */
public class GameActivity extends AppCompatActivity {

    private Game game;  //the game itself


    /* Stages of the activity lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GameActivity.java","onCreate()");

        setContentView(R.layout.activity_game);

        game = new Game(this);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("GameActivity.java","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GameActivity.java","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("GameActivity.java","onResume()");

        game.pause();   //avoids crash ex: if back button is pressed
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("GameActivity.java","onStop()");
    }

    //TODO: make sure that activities are closed, there are not multiple activities of same type running
    //https://developer.android.com/guide/components/activities/tasks-and-back-stack
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameActivity.java","onDestroy()");
    }


    /* other methods */

    @Override
    public void onBackPressed() {
        //super.onBackPressed();    //disabled back button, to avoid accidental touch
    }
}