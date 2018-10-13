package com.example.usmankhan.quizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.usmankhan.quizapplication.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SmartQuizApp";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String CREATE_QUESTION = "Create TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_NAME + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSNO + " INTEGER " +
                " ) ";

        db.execSQL( CREATE_QUESTION );
        FillQuestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( " DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME );
        onCreate( db );

    }


    public void FillQuestionTable() {
        Question question = new Question( "A is a Correct", "A", "B", "c", "D", 1 );
        AddQuestion(question);
        Question question1 = new Question( "B is a Correct", "A", "B", "c", "D", 2 );
        AddQuestion(question1);
        Question question2 = new Question( "C is a Correct", "A", "B", "c", "D", 3 );
        AddQuestion(question2);
        Question question3 = new Question( "D is a Correct", "A", "B", "c", "D", 4 );
        AddQuestion(question3);
        Question question4 = new Question( "A is Again Correct", "A", "B", "c", "D", 1 );
        AddQuestion(question4);
        Question question5 = new Question( "B is Again Correct", "A", "B", "c", "D", 2 );
        AddQuestion(question5);

    }

    private void AddQuestion(Question question) {
        ContentValues cv = new ContentValues();

        cv.put( QuestionTable.COLUMN_NAME, question.getQuestion() );
        cv.put( QuestionTable.COLUMN_OPTION1, question.getOption1() );
        cv.put( QuestionTable.COLUMN_OPTION2, question.getOption2() );
        cv.put( QuestionTable.COLUMN_OPTION3, question.getOption3() );
        cv.put( QuestionTable.COLUMN_OPTION4, question.getOption4() );
        cv.put( QuestionTable.COLUMN_ANSNO, question.getAnswerNr() );

        db.insert( QuestionTable.TABLE_NAME,null,cv );

    }
    public List<Question> getAllQuestions(){
        List<Question> questionsList=new ArrayList<>();

        db=getReadableDatabase();

        Cursor cursor=db.rawQuery( "Select * from "+QuestionTable.TABLE_NAME,null );
        if (cursor.moveToFirst()){
            do {
             Question question=new Question();
             question.setQuestion( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_NAME ) ) );
             question.setOption1( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION1 ) ) );
             question.setOption2( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION2 ) ) );
             question.setOption3( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION3 ) ) );
             question.setOption4( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION4 ) ) );
             question.setAnswerNr( cursor.getInt( cursor.getColumnIndex( QuestionTable.COLUMN_ANSNO ) ) );
             questionsList.add( question );

            }while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;

    }

}
