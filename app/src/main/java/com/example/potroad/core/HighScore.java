package com.example.potroad.core;

public class HighScore implements Comparable<HighScore>{
    private final String name;
    private final int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(HighScore highScore) {
        return Integer.compare(highScore.score,this.score);//reverse ordering
    }
}
