package com.example.usmankhan.quizapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questiontext, scoretext, question_count_text, time_text, score_counts;
    private RadioGroup rb;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button confirmNext;

    private ColorStateList default_Text_Color;

    private List<Question> questionList;
    private int question_Counter;
    private int totalQuestion_Count;
    private Question current_Question;

    private int Score;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_quiz );
        questiontext = (TextView) findViewById( R.id.text_view_question );
        scoretext = (TextView) findViewById( R.id.score_count );
        question_count_text = (TextView) findViewById( R.id.question_count );
        time_text = (TextView) findViewById( R.id.time_count );
        rb = findViewById( R.id.radio_group );
        rb1 = findViewById( R.id.btn_1 );
        rb2 = findViewById( R.id.btn_2 );
        rb3 = findViewById( R.id.btn_3 );
        rb4 = findViewById( R.id.btn_4 );
        confirmNext = (Button) findViewById( R.id.confirm_btn );

        default_Text_Color = rb1.getTextColors();


        QuizHelper db = new QuizHelper( this );
        questionList = db.getAllQuestions();
        totalQuestion_Count = questionList.size();
        Collections.shuffle( questionList );

        ShowNextQuestion();

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
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;

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
        finish();
    }
}
