package com.example.potroad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.potroad.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity.java","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity.java","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity.java","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity.java","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity.java","onDestroy()");
    }

    public void startButtonListener(View view){
        startActivity(new Intent(this, GameActivity.class));
    }

    public void highScoresButtonListener(View view){
        startActivity(new Intent(this, HighScoreActivity.class));
    }


}