package com.example.usmankhan.quizapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String Extra_Socre = "extraSore";
    private static final long COUNT_DOWN_MILLISECOND = 30000;

    private static final String KEY_SCORE = "KeyScore";
    private static final String KEY_QUESTION_COUNT = "KeyQuestionCount";
    private static final String KEY_MILLIES_LEFT = "KeyMilliesLeft";
    private static final String KEY_ANSWERED = "KeyAnswer";
    private static final String KEY_QUESTION_LIST = "KeyQuestionList";


    private TextView questiontext, scoretext, question_count_text, time_text, score_counts;
    private TextView difficulty_text;
    private RadioGroup rb;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button confirmNext;

    private ColorStateList default_Text_Color;
    private ColorStateList defaultColorCD;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillisecond;

    private ArrayList<Question> questionList;
    private int question_Counter;
    private int totalQuestion_Count;
    private Question current_Question;

    private int Score;
    private boolean answered;

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quiz );
        questiontext = (TextView) findViewById( R.id.text_view_question );
        scoretext = (TextView) findViewById( R.id.score_count );
        question_count_text = (TextView) findViewById( R.id.question_count );

        difficulty_text = findViewById( R.id.difficulty_text_View );

        time_text = (TextView) findViewById( R.id.time_count );
        rb = findViewById( R.id.radio_group );
        rb1 = findViewById( R.id.btn_1 );
        rb2 = findViewById( R.id.btn_2 );
        rb3 = findViewById( R.id.btn_3 );
        rb4 = findViewById( R.id.btn_4 );
        confirmNext = (Button) findViewById( R.id.confirm_btn );

        default_Text_Color = rb1.getTextColors();
        defaultColorCD = time_text.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra( MainActivity.EXTRA_DIFFICULTY );
        difficulty_text.setText( "Difficulty :" + difficulty );


        if (savedInstanceState == null) {
            QuizHelper db = new QuizHelper( this );
            questionList = db.getQuestions( difficulty );//here we pass difficulty levels
            totalQuestion_Count = questionList.size();
            Collections.shuffle( questionList );
            ShowNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList( KEY_QUESTION_LIST );
            //if (questionList==null){finish();}
            totalQuestion_Count = questionList.size();
            question_Counter = savedInstanceState.getInt( KEY_QUESTION_COUNT );
            current_Question = questionList.get( question_Counter - 1 );
            Score = savedInstanceState.getInt( KEY_SCORE );
            timeLeftinMillisecond = savedInstanceState.getInt( KEY_MILLIES_LEFT );
            answered = savedInstanceState.getBoolean( KEY_ANSWERED );
            if (!answered) {
                startCountDown();
            } else {
                UpdateCountDownText();
                showSolution();
            }
        }


        confirmNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText( QuizActivity.this, "Please Select the Options", Toast.LENGTH_SHORT ).show();

                    }
                } else {
                    ShowNextQuestion();
                }
            }
        } );
    }

    private void ShowNextQuestion() {
        rb1.setTextColor( default_Text_Color );
        rb2.setTextColor( default_Text_Color );
        rb3.setTextColor( default_Text_Color );
        rb4.setTextColor( default_Text_Color );
        rb.clearCheck();

        if (question_Counter < totalQuestion_Count) {
            current_Question = questionList.get( question_Counter );
            questiontext.setText( current_Question.getQuestion() );

            rb1.setText( current_Question.getOption1() );
            rb2.setText( current_Question.getOption2() );
            rb3.setText( current_Question.getOption3() );
            rb4.setText( current_Question.getOption4() );

            question_Counter++;

            question_count_text.setText( "Question: " + question_Counter + "/" + totalQuestion_Count );
            answered = false;
            confirmNext.setText( "confirmed" );
            timeLeftinMillisecond = COUNT_DOWN_MILLISECOND;
            startCountDown();
        } else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer( timeLeftinMillisecond, 1000 ) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMillisecond = millisUntilFinished;
                UpdateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftinMillisecond = 0;
                UpdateCountDownText();
                checkAnswer();

            }
        }.start();
    }

    private void UpdateCountDownText() {
        int millies = (int) (timeLeftinMillisecond / 1000) / 60;
        int seconds = (int) (timeLeftinMillisecond / 1000) % 60;
        String TimeFormated = String.format( Locale.getDefault(), "%02d:%02d", millies, seconds );
        time_text.setText( TimeFormated );
        if (timeLeftinMillisecond < 10000) {
            time_text.setTextColor( Color.RED );
        } else {
            time_text.setTextColor( defaultColorCD );
        }

    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton radioButton = findViewById( rb.getCheckedRadioButtonId() );
        int AnswerNr = rb.indexOfChild( radioButton ) + 1;
        if (AnswerNr == current_Question.getAnswerNr()) {
            Score++;
            scoretext.setText( "Score: " + Score );
        }
        showSolution();
    }

    public void showSolution() {
        rb1.setTextColor( Color.RED );
        rb2.setTextColor( Color.RED );
        rb3.setTextColor( Color.RED );
        rb4.setTextColor( Color.RED );

        switch (current_Question.getAnswerNr()) {
            case 1:
                rb1.setTextColor( Color.GREEN );
                questiontext.setText( "Answer 1 is Correct " );
                break;
            case 2:
                rb2.setTextColor( Color.GREEN );
                questiontext.setText( "Answer 2 is Correct " );
                break;
            case 3:
                rb3.setTextColor( Color.GREEN );
                questiontext.setText( "Answer 3 is Correct " );
                break;
            case 4:
                rb4.setTextColor( Color.GREEN );
                questiontext.setText( "Answer 1 is Correct " );
                break;

        }
        if (question_Counter < totalQuestion_Count) {
            confirmNext.setText( "Next" );

        } else {
            confirmNext.setText( "finished" );
        }

    }

    public void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra( Extra_Socre, Score );
        setResult( RESULT_OK, resultIntent );
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText( this, "Press Again to CAncle that Quiz", Toast.LENGTH_SHORT ).show();

        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        outState.putInt( KEY_SCORE, Score );
        outState.putInt( KEY_QUESTION_COUNT, question_Counter );
        outState.putBoolean( KEY_ANSWERED, answered );
        outState.putLong( KEY_MILLIES_LEFT, timeLeftinMillisecond );
        outState.putParcelableArrayList( KEY_QUESTION_LIST, questionList );

    }
}
