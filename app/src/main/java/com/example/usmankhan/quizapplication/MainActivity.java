package com.example.usmankhan.quizapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.icu.util.ULocale;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryId";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    private Spinner spinnercategory;
    private Spinner spinnerDifficulty;

    public static final String SHARED_PREF = "SharedPref";
    public static final String KEY_HIGH = "HighScoreKey";
    private TextView highScoreText;
    private int highScore;


    TextView t;
    Button s_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        highScoreText=(TextView) findViewById( R.id.high_score );
        spinnercategory = findViewById( R.id.spinner_category );
        spinnerDifficulty = findViewById( R.id.spinner_difficulty );


        LoadQuizCategory();
        LoadDifficultyLevels();
        LoadHighScore();


        s_btn = (Button) findViewById( R.id.start_btn );

        s_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();

            }
        } );

        t = (TextView) findViewById( R.id.text_view );
        Typeface typeface = Typeface.createFromAsset( getAssets(), "fonts/old-english-gothic-1361508485.ttf" );
        t.setTypeface( typeface );
    }

    private void startQuiz() {
        QuizCategory selectedCategory = (QuizCategory) spinnercategory.getSelectedItem();
        int categoryId = selectedCategory.getId();
        String categoryName = selectedCategory.getName();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent( MainActivity.this, QuizActivity.class );
        intent.putExtra( EXTRA_CATEGORY_ID, categoryId );
        intent.putExtra( EXTRA_CATEGORY_NAME, categoryName );
        intent.putExtra( EXTRA_DIFFICULTY, difficulty );

        startActivityForResult( intent, REQUEST_CODE_QUIZ );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra( QuizActivity.Extra_Socre, 0 );
                if (score > highScore) {
                    UpdateHighScore( score );
                }
            }
        }
    }

    private void LoadQuizCategory() {
        QuizHelper quizHelper = QuizHelper.getInstance( this );
        List<QuizCategory> categoryList = quizHelper.getAllCategory();
        ArrayAdapter<QuizCategory> adapterCategories = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_item, categoryList );
        adapterCategories.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnercategory.setAdapter( adapterCategories );

    }

    private void LoadDifficultyLevels() {

        String[] diffLevels = Question.GetAllDifficultyLevel();

        ArrayAdapter<String> questionArrayAdapter = new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_item, diffLevels );
        spinnerDifficulty.setAdapter( questionArrayAdapter );
        questionArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
    }


    private void LoadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences( SHARED_PREF, MODE_PRIVATE );
        highScore = sharedPreferences.getInt( KEY_HIGH, 0 );
        highScoreText.setText( "High Score: " + highScore );
    }

    private void UpdateHighScore(int NewHighScore) {
        highScore = NewHighScore;
        highScoreText.setText( "High Score: " + highScore );
        SharedPreferences sharedPreferences = getSharedPreferences( SHARED_PREF, MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt( KEY_HIGH, highScore );
        editor.apply();


    }
}