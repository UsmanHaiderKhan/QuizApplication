package com.example.usmankhan.quizapplication;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class QuestionTable implements BaseColumns
    {
        public static final String TABLE_NAME="quiz_question";
        public static final String COLUMN_NAME="Question";
        public static final String COLUMN_OPTION1="Option1";
        public static final String COLUMN_OPTION2="Option2";
        public static final String COLUMN_OPTION3="Option3";
        public static final String COLUMN_OPTION4="Option4";
        public static final String COLUMN_ANSNO="AnswerNr";
        public static final String COLUMN_Difficulty = "difficulty";

    }
}
