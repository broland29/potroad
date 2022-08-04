package com.example.potroad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startButtonListener(View view){
        //Button startButton = (Button) findViewById(R.id.start_button);
        startActivity(new Intent(this,GameActivity.class));
    }

    public void debug(View view){
        startActivity(new Intent(this,GameOverActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity.java","onDestroy()");
    }

}