package com.example.usmankhan.quizapplication;

public class QuizCategory {
    public static final int Programming = 1;
    public static final int GeoGraphy = 2;
    public static final int Math = 3;

    private int Id;
    private String Name;


    public QuizCategory() {
    }

    public QuizCategory(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

