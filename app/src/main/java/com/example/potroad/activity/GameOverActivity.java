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

import androidx.appcompat.app.AlertDialog;

import com.example.potroad.R;

/**
 * The activity which shows up when the player loses all of its health
 *  - launched from GameActivity
 *  - gives the options to play again, go back to home screen, submit high score
 *  - brings to MainActivity, GameActivity or HighScoreActivity (through submitPopupView)
 */
public class GameOverActivity extends Activity {

    private Dialog dialog;
    private View highScorePopup;


    /* Stages of the activity lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("GameOverActivity.java","onCreate()");

        setContentView(R.layout.activity_game_over);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int)(displayMetrics.widthPixels * .5);
        int height = (int)(displayMetrics.heightPixels * .5);

        getWindow().setLayout(width,height);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("GameOverActivity.java","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GameOverActivity.java","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("GameOverActivity.java","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("GameOverActivity.java","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameOverActivity.java","onDestroy()");
    }


    /* on click methods for buttons */

    public void againButtonOnClick(View view){
        Intent intent = new Intent(this, GameActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void homeButtonOnClick(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void submitButtonOnClick(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        highScorePopup = getLayoutInflater().inflate(R.layout.popup_high_score,null);

        dialogBuilder.setView(highScorePopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    /*
    public void submitButtonOnClick(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View submitPopupView = getLayoutInflater().inflate(R.layout.popup_high_score, null);

        dialogBuilder.setView(submitPopupView);
        Dialog dialog = dialogBuilder.create();
        dialog.show();

        Button backButton = submitPopupView.findViewById(R.id.back_button_popup_high_score);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GameOverActivity.java","submitHighScoreButtonListener() -> backButton");
                dialog.dismiss();
            }
        });

        EditText editText = submitPopupView.findViewById(R.id.name_text_popup_high_score);

        Button submitButton = submitPopupView.findViewById(R.id.submit_button_popup_high_score);
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

    }*/


    /* on click methods for buttons of popup */

    public void popupBackButtonOnClick(View view) {
        Log.d("GameOverActivity.java","submitHighScoreButtonListener() -> backButton");
        dialog.dismiss();
    }

    public void popupSubmitButtonOnClick(View view) {
        EditText editText = highScorePopup.findViewById(R.id.name_text_popup_high_score);

        Intent intent = new Intent(GameOverActivity.this,HighScoreActivity.class);

        //toSting is vital here, else cannot get it back from intent as string
        //TODO: maybe some validation of nickname
        intent.putExtra("nickname",(editText.getText().toString()));

        //get from previous intent, pass on to put in table
        intent.putExtra("score",getIntent().getDoubleExtra("score",-1));

        startActivity(intent);
    }
}
