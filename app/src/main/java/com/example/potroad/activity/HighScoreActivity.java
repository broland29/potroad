package com.example.potroad.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.potroad.HighScore;
import com.example.potroad.R;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "highScores";
    private ArrayList<HighScore> highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HighScoreActivity.java","onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        highScores = new ArrayList<>();
        loadData();
    }

    @Override
    protected void onResume() {
        Log.d("HighScoreActivity.java","onResume()");
        super.onResume();

        //get nickname from intent (from popup)
        String nickname = getIntent().getStringExtra("nickname");
        int score = (int)(getIntent().getDoubleExtra("score",-2));
        Log.d("HighScoreActivity.java","nickname : " + nickname);

        //invalid data / nothing comes from intent
        if (nickname == null){
            Log.d("HighScoreActivity.java","nickname is null!");
            return;
        }
        if (score == -1){
            Log.d("HighScoreActivity.java","score is -1!");
            return;
        }
        if (score == -3){
            Log.d("HighScoreActivity.java","score is -3!");
            return;
        }

        highScores.add(new HighScore(nickname,score));
        Collections.sort(highScores);

        //https://stackoverflow.com/questions/55411752/how-to-get-tablelayout-cell-values-in-android
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        for(int i=0; i<highScores.size(); i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i+1);//first row reserved, offset of 1
            TextView nameTextView = (TextView) row.getChildAt(1);
            TextView scoreTextView = (TextView) row.getChildAt(2);
            nameTextView.setText(highScores.get(i).getName());
            scoreTextView.setText(String.valueOf(highScores.get(i).getScore()));
        }
    }

    //https://www.youtube.com/watch?v=fJEFZ6EOM9o
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int numberOfHighScores = highScores.size();
        editor.putInt("numberOfHighScores",numberOfHighScores);
        for(int i=0; i<numberOfHighScores; i++){
            editor.putString("name" + i, highScores.get(i).getName());
            editor.putInt("score" + i, highScores.get(i).getScore());
        }

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        int numberOfHighScores = sharedPreferences.getInt("numberOfHighScores",0);
        for(int i=0; i<numberOfHighScores; i++){
            highScores.add(new HighScore(
                    sharedPreferences.getString("name" + i,"N/A"),
                    sharedPreferences.getInt("score" + i,-404)
            ));
        }
    }

    public void backButtonListener(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void resetButtonListener(View view){

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for(int i=0; i<highScores.size(); i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i+1);//first row reserved, offset of 1
            TextView nameTextView = (TextView) row.getChildAt(1);
            TextView scoreTextView = (TextView) row.getChildAt(2);
            nameTextView.setText("");
            scoreTextView.setText("");
        }

        highScores.clear();
    }

    @Override
    protected void onPause() {
        Log.d("HighScoreActivity.java","onPause()");
        saveData();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("HighScoreActivity.java","onDestroy()");
        super.onDestroy();
    }


}