package com.example.usmankhan.quizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.QuoteSpan;

import com.example.usmankhan.quizapplication.QuizContract.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuizHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SmartQuizApp";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private static QuizHelper instance;


    private QuizHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public static synchronized QuizHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizHelper( context.getApplicationContext() );

        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUIZCATEGORIES_TABLE = "CREATE TABLE " +
                QuizCategoryTable.TABLE_NAME + "( " +
                QuizCategoryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizCategoryTable.COLUMN_NAME + " TEXT " +
                ")";


        final String CREATE_QUESTION = "Create TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_NAME + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSNO + " INTEGER, " +
                QuestionTable.COLUMN_Difficulty + " TEXT, " +
                QuestionTable.COLUMN_QUIZCATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_QUIZCATEGORY_ID + ") REFERENCES " +
                QuizCategoryTable.TABLE_NAME + "(" + QuizCategoryTable._ID + ")" +
                "ON DELETE CASCADE" +
                ")";
        db.execSQL( SQL_CREATE_QUIZCATEGORIES_TABLE );
        db.execSQL( CREATE_QUESTION );
        FillCategoryTable();
        FillQuestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( " DROP TABLE IF EXISTS " + QuizCategoryTable.TABLE_NAME );
        db.execSQL( " DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME );

        onCreate( db );

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure( db );
        db.setForeignKeyConstraintsEnabled( true );
    }


    private void FillCategoryTable() {
        QuizCategory category = new QuizCategory( "Programming" );
        addCategory( category );
        QuizCategory category1 = new QuizCategory( "GeoGraphy" );
        addCategory( category1 );
        QuizCategory category2 = new QuizCategory( "Math" );
        addCategory( category2 );

    }

    private void addCategory(QuizCategory category) {
        ContentValues cv = new ContentValues();
        cv.put( QuizCategoryTable.COLUMN_NAME, category.getName() );
        db.insert( QuizCategoryTable.TABLE_NAME, null, cv );

    }
    public void FillQuestionTable() {
        Question question = new Question( "Programming,EASY:Static Class Means in Java..?", "Directly Call", "By Name", "By Value", "By Reffrence"
                , 1, Question.DIFFICULTY_EASY, QuizCategory.Programming );
        AddQuestion(question);

        Question question1 = new Question( "Programming,Medium: Perpouse of interface in programming", "Hiding Implementation", "Show Implementation", "Interact with user", "Control Function"
                , 2, Question.DIFFICULTY_MEDIUM, QuizCategory.Programming );
        AddQuestion(question1);

        Question question3 = new Question( "Programming,Hard: Full Form of OOP ", "off off pops", "object oriented Pakistan", "object oriented Programming", "object oriented People"
                , 3, Question.DIFFICULTY_HARD, QuizCategory.Programming );
        AddQuestion(question3);

        Question question4 = new Question( "Programming,EASY: How Many Types of C Languages ", "One", "Two", "Four", "Three"
                , 4, Question.DIFFICULTY_EASY, QuizCategory.Programming );
        AddQuestion(question4);

        Question question5 = new Question( "Programming,Medium: C# is Strongly______Based Language ", "Structured ", "OOP", "Unified", "Functional"
                , 2, Question.DIFFICULTY_MEDIUM, QuizCategory.Programming );
        AddQuestion(question5);


        Question question6 = new Question( "GeoGraphy,EASY: Total Area Of Pakistan is..? ", "1799902 ", "198001", "745636", "674523"
                , 1, Question.DIFFICULTY_EASY, QuizCategory.GeoGraphy );
        AddQuestion( question6 );

        Question question7 = new Question( "GeoGraphy,Medium: Biggest Mountain of Pakistan is ..? ", "Gelgat ", "K-TWO", "Murre", "Rawatt"
                , 2, Question.DIFFICULTY_MEDIUM, QuizCategory.GeoGraphy );
        AddQuestion( question7 );

        Question question8 = new Question( "GeoGraphy,Hard: How Many River of Pakistan ..? ", "One ", "Two", "five", "Eight"
                , 3, Question.DIFFICULTY_HARD, QuizCategory.GeoGraphy );
        AddQuestion( question8 );

        Question question9 = new Question( "GeoGraphy,Medium: Total Muslim Countries in the Whole World ..? ", "50 ", "40", "60", "56"
                , 4, Question.DIFFICULTY_MEDIUM, QuizCategory.GeoGraphy );
        AddQuestion( question9 );

        Question question10 = new Question( "GeoGraphy,Medium,Current PM Of Pakistan Name is..? ", "Imran Khan ", "Usman Khan", "Saim Khan", "Salman Khan"
                , 1, Question.DIFFICULTY_MEDIUM, QuizCategory.GeoGraphy );
        AddQuestion( question10 );


        Question question11 = new Question( "MAth,Easy,Two + Two Make... ", "4 ", "9", "3", "22"
                , 1, Question.DIFFICULTY_EASY, QuizCategory.Math );
        AddQuestion( question11 );

        Question question12 = new Question( "MAth,Medium,34 + 34 Make... ", "2345 ", "68", "69", "70"
                , 2, Question.DIFFICULTY_MEDIUM, QuizCategory.Math );
        AddQuestion( question12 );

        Question question13 = new Question( "MAth,HArd,78-20 Make... ", "89 ", "69", "58", "59"
                , 3, Question.DIFFICULTY_HARD, QuizCategory.Math );
        AddQuestion( question13 );

        Question question14 = new Question( "MAth,HArd,5%50 Left Remaining... ", "12 ", "69", "8", "0"
                , 4, Question.DIFFICULTY_HARD, QuizCategory.Math );
        AddQuestion( question14 );

        Question question15 = new Question( "MAth,HArd,-67-2 Make Total... ", "-45 ", "-69", "+69", "-70"
                , 2, Question.DIFFICULTY_HARD, QuizCategory.Math );
        AddQuestion( question15 );

    }

    private void AddQuestion(Question question) {
        ContentValues cv = new ContentValues();

        cv.put( QuestionTable.COLUMN_NAME, question.getQuestion() );
        cv.put( QuestionTable.COLUMN_OPTION1, question.getOption1() );
        cv.put( QuestionTable.COLUMN_OPTION2, question.getOption2() );
        cv.put( QuestionTable.COLUMN_OPTION3, question.getOption3() );
        cv.put( QuestionTable.COLUMN_OPTION4, question.getOption4() );
        cv.put( QuestionTable.COLUMN_ANSNO, question.getAnswerNr() );
        cv.put( QuestionTable.COLUMN_Difficulty, question.getDifficulty() );
        cv.put( QuestionTable.COLUMN_QUIZCATEGORY_ID, question.getCategoryId() );
        db.insert( QuestionTable.TABLE_NAME,null,cv );

    }

    public List<QuizCategory> getAllCategory() {
        List<QuizCategory> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery( " SELECT * FROM " + QuizCategoryTable.TABLE_NAME, null );
        if (c.moveToNext()) {
            do {
                QuizCategory category = new QuizCategory();
                category.setId( c.getInt( c.getColumnIndex( QuizCategoryTable._ID ) ) );
                category.setName( c.getString( c.getColumnIndex( QuizCategoryTable.COLUMN_NAME ) ) );
                categoryList.add( category );

            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }


    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionsList = new ArrayList<>();

        db=getReadableDatabase();

        Cursor cursor = db.rawQuery( "Select * from " + QuestionTable.TABLE_NAME, null );
        if (cursor.moveToFirst()){
            do {
             Question question=new Question();
                question.setId( cursor.getInt( cursor.getColumnIndex( QuestionTable._ID ) ) );
             question.setQuestion( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_NAME ) ) );
             question.setOption1( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION1 ) ) );
             question.setOption2( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION2 ) ) );
             question.setOption3( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION3 ) ) );
             question.setOption4( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION4 ) ) );
             question.setAnswerNr( cursor.getInt( cursor.getColumnIndex( QuestionTable.COLUMN_ANSNO ) ) );
                question.setDifficulty( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_Difficulty ) ) );
                question.setCategoryId( cursor.getInt( cursor.getColumnIndex( QuestionTable.COLUMN_QUIZCATEGORY_ID ) ) );

             questionsList.add( question );

            }while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;

    }

    public ArrayList<Question> getQuestions(int categoryId, String difficulty) {
        ArrayList<Question> questionsList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionTable.COLUMN_QUIZCATEGORY_ID + " =? " +
                " AND " + QuestionTable.COLUMN_Difficulty + " =? ";

        String[] selectionArgs = new String[]{String.valueOf( categoryId ), difficulty};
        Cursor cursor = db.query( QuestionTable.TABLE_NAME, null, selection
                , selectionArgs, null, null, null );
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId( cursor.getInt( cursor.getColumnIndex( QuestionTable._ID ) ) );
                question.setQuestion( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_NAME ) ) );
                question.setOption1( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION1 ) ) );
                question.setOption2( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION2 ) ) );
                question.setOption3( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION3 ) ) );
                question.setOption4( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_OPTION4 ) ) );
                question.setAnswerNr( cursor.getInt( cursor.getColumnIndex( QuestionTable.COLUMN_ANSNO ) ) );
                question.setDifficulty( cursor.getString( cursor.getColumnIndex( QuestionTable.COLUMN_Difficulty ) ) );
                question.setCategoryId( cursor.getInt( cursor.getColumnIndex( QuestionTable.COLUMN_QUIZCATEGORY_ID ) ) );
                questionsList.add( question );

            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;

    }

}
