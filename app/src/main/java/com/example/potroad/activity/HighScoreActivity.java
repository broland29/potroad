package com.example.potroad.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.potroad.HighScore;
import com.example.potroad.R;

import java.util.ArrayList;

/**
 *  The activity which shows the high scores in a table
 *   - launched by MainActivity or GameOverActivity (through submitPopupView)
 *   - brings to MainActivity
 */
public class HighScoreActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "highScores";
    private ArrayList<HighScore> highScores;                            //list of all high scores

    /* Stages of the activity lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HighScoreActivity.java","onCreate()");

        setContentView(R.layout.activity_high_score);
        highScores = new ArrayList<>();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("HighScoreActivity.java","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HighScoreActivity.java","onResume()");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String nickname;
        int score;
        int highScoresSize = highScores.size();

        //check for extras
        if (extras == null){
            Log.d("HighScoreActivity.java", "No extras found.");
        }
        else{
            //check for nickname
            if (!extras.containsKey("nickname")){
                Log.d("HighScoreActivity.java", "No nickname found.");
            }
            else if (!extras.containsKey("score")){
                Log.d("HighScoreActivity.java", "No score found.");
            }
            else{
                nickname = extras.getString("nickname");
                score = extras.getInt("score");

                //to avoid submitting the same score multiple times
                getIntent().removeExtra("nickname");
                getIntent().removeExtra("score");

                HighScore highScore = new HighScore(nickname,score);

                if (highScoresSize == 10 && highScores.get(9).compareTo(highScore) <= 0){
                    //high score not displayed since too low
                    Toast.makeText(getApplicationContext(),R.string.bad_high_score,Toast.LENGTH_SHORT).show();
                }
                else{
                    //high score in top 10
                    Toast.makeText(getApplicationContext(),R.string.good_high_score,Toast.LENGTH_SHORT).show();

                    int place = 0;

                    //find the right place
                    while (place < 10 && place < highScoresSize && highScores.get(place).compareTo(highScore) <= 0){
                        place++;
                    }

                    //insert there
                    highScores.add(place,highScore);

                    //to keep size <= 10, remove last high score if needed
                    if(highScores.size() == 11){
                        highScores.remove(highScores.get(10));
                    }
                    else{
                        highScoresSize++;
                    }
                }
            }
        }

        //fill table; highScoreSize should not exceed 10!
        //https://stackoverflow.com/questions/55411752/how-to-get-tablelayout-cell-values-in-android
        TableLayout tableLayout = findViewById(R.id.table_layout_high_score);
        for(int i=0; i<highScoresSize; i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i + 1);      //first row reserved, offset of 1

            TextView nameTextView = (TextView) row.getChildAt(1);
            TextView scoreTextView = (TextView) row.getChildAt(2);

            nameTextView.setText(highScores.get(i).getName());
            scoreTextView.setText(String.valueOf(highScores.get(i).getScore()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HighScoreActivity.java","onPause()");

        saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("HighScoreActivity.java","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("HighScoreActivity.java","onDestroy()");
    }


    /* on click methods for buttons */

    public void backButtonOnClick(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void resetButtonOnClick(View view){

        TableLayout tableLayout = findViewById(R.id.table_layout_high_score);

        for(int i=0; i<highScores.size(); i++){
            TableRow row = (TableRow) tableLayout.getChildAt(i+1);//first row reserved, offset of 1
            TextView nameTextView = (TextView) row.getChildAt(1);
            TextView scoreTextView = (TextView) row.getChildAt(2);
            nameTextView.setText("");
            scoreTextView.setText("");
        }

        highScores.clear();
    }


    /* other methods */

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
}