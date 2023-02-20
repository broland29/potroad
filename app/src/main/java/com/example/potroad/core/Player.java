package com.example.potroad.core;

import android.graphics.Canvas;

import com.example.potroad.graphics.PlayerAnimator;
import com.example.potroad.graphics.SpriteSheet;
import com.example.potroad.panel.Score;

public class Player extends Rectangle{

    int currentRoad = 3;

    private double gamePoints;
    private int healthPoints;

    private final PlayerAnimator playerAnimator;


    public Player(double positionX, double positionY, double width, double height, int color, SpriteSheet spriteSheet) {
        super(positionX, positionY, width, height, color);
        gamePoints = 0;
        healthPoints = 3;

        playerAnimator = new PlayerAnimator(spriteSheet.getPlayerSprites(this));
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas,gameDisplay);
        playerAnimator.draw(canvas,this);
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
