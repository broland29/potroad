package com.example.potroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GameOverActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int)(displayMetrics.widthPixels * .5);
        int height = (int)(displayMetrics.heightPixels * .5);

        getWindow().setLayout(width,height);
    }

    public void repeatButtonAction(View view){
        Intent intent = new Intent(this,GameActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void homeButtonAction(View view){
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameOverActivity.java","onDestroy()");
    }
}
