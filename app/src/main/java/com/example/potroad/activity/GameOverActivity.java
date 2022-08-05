package com.example.potroad.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.potroad.R;

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

    public void repeatButtonListener(View view){
        Intent intent = new Intent(this, GameActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void homeButtonListener(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void submitHighScoreButtonListener(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View submitPopupView = getLayoutInflater().inflate(R.layout.popup_high_score, null);

        dialogBuilder.setView(submitPopupView);
        Dialog dialog = dialogBuilder.create();
        dialog.show();

        Button backButton = submitPopupView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GameOverActivity.java","submitHighScoreButtonListener() -> backButton");
                dialog.dismiss();
            }
        });

        EditText editText = submitPopupView.findViewById(R.id.highScoreInput);

        Button submitButton = submitPopupView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GameOverActivity.java","submitHighScoreButtonListener() -> submitButton");
                Intent intent = new Intent(GameOverActivity.this,HighScoreActivity.class);
                Log.d("GameOverActivity.java","nickname : " + editText.getText());
                intent.putExtra("nickname",(editText.getText().toString()));    //toSting is vital here, else cannot get it back from intent as string                                    //TODO: maybe some validation of nickname
                intent.putExtra("score",getIntent().getDoubleExtra("score",-1));    //get from previous intent, pass on to put in table
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameOverActivity.java","onDestroy()");
    }
}
