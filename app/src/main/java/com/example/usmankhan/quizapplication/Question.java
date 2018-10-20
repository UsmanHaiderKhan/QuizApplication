package com.example.usmankhan.quizapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_HARD = "Hard";
    public static final String DIFFICULTY_MEDIUM = "Medium";

    private String Question;
    private String Option1;
    private String Option2;
    private String Option3;
    private String Option4;
    private int AnswerNr;
    private String Difficulty;

    public Question(){}


    public Question(String question, String option1, String option2,
                    String option3, String option4, int answerNr, String difficulty) {
        Question = question;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
        AnswerNr = answerNr;
        Difficulty = difficulty;
    }

    protected Question(Parcel in) {

        Question = in.readString();
        Option1 = in.readString();
        Option2 = in.readString();
        Option3 = in.readString();
        Option4 = in.readString();
        AnswerNr = in.readInt();
        Difficulty = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question( in );
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOption1() {
        return Option1;
    }

    public void setOption1(String option1) {
        Option1 = option1;
    }

    public String getOption2() {
        return Option2;
    }

    public void setOption2(String option2) {
        Option2 = option2;
    }

    public String getOption3() {
        return Option3;
    }

    public void setOption3(String option3) {
        Option3 = option3;
    }

    public String getOption4() {
        return Option4;
    }

    public void setOption4(String option4) {
        Option4 = option4;
    }

    public int getAnswerNr() {
        return AnswerNr;
    }

    public void setAnswerNr(int answerNr) {
        AnswerNr = answerNr;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public static String[] GetAllDifficultyLevel() {
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( Question );
        dest.writeString( Option1 );
        dest.writeString( Option2 );
        dest.writeString( Option3 );
        dest.writeString( Option4 );
        dest.writeInt( AnswerNr );
        dest.writeString( Difficulty );
    }
}
