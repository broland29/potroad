package com.example.potroad.object;

import com.example.potroad.panel.Score;

public class Player extends Rectangle{

    int currentRoad = 3;

    private double gamePoints;
    private int healthPoints;

    public Player(double positionX, double positionY, double width, double height, int color) {
        super(positionX, positionY, width, height, color);
        gamePoints = 0;
        healthPoints = 3;
    }

    public void jumpLeft(float roadWidth){
        if (currentRoad > 1){
            positionX -= roadWidth;
            currentRoad--;
        }
    }

    public void jumpRight(float roadWidth){
        if (currentRoad < 5){
            positionX += roadWidth;
            currentRoad++;
        }
    }

    @Override
    public void update() {
        gamePoints += Score.POINTS_PER_UPDATE;
    }

    public double getGamePoints() {
        return gamePoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
