package com.example.potroad.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.potroad.R;

import java.util.regex.Pattern;

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

        int width = (int)(displayMetrics.widthPixels * .75);
        int height = (int)(displayMetrics.heightPixels * .75);

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


    /* on click methods for buttons of popup */

    public void popupBackButtonOnClick(View view) {
        Log.d("GameOverActivity.java","submitHighScoreButtonListener() -> backButton");
        dialog.dismiss();
    }

    public void popupSubmitButtonOnClick(View view) {
        EditText inputEditText = highScorePopup.findViewById(R.id.name_text_popup_high_score);
        TextView messageTextView = highScorePopup.findViewById(R.id.message_popup_high_score);

        //get user input and validate it
        String nickname = inputEditText.getText().toString();
        if (!Pattern.matches("[a-zA-Z0-9]*",nickname)){
            messageTextView.setText(R.string.bad_nickname_popup_high_score);
            return;
        }

        //get from previous intent
        int score = getIntent().getIntExtra("score",-1);
        if (score == -1){
            messageTextView.setText(R.string.bad_score_popup_high_score);
            return;
        }

        // pass data through intent
        Intent intent = new Intent(GameOverActivity.this,HighScoreActivity.class);
        intent.putExtra("nickname",nickname);
        intent.putExtra("score",score);

        startActivity(intent);
    }
}
