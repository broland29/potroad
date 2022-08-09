package com.example.potroad.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.potroad.object.GameDisplay;
import com.example.potroad.object.Player;

public class PlayerAnimator {
    private final Sprite[] sprites;

    //needs to be in sync with SpriteSheet.getPlayerSprites()
    private static final int INDEX_3_HEALTH = 0;
    private static final int INDEX_2_HEALTH = 1;
    private static final int INDEX_1_HEALTH = 2;
    private static final int INDEX_0_HEALTH = 3;

    public PlayerAnimator(Sprite[] sprites) {
        this.sprites = sprites;
    }

    public void draw(Canvas canvas, Player player){
        switch (player.getHealthPoints()){
            case 3:
                drawFrame(canvas,sprites[INDEX_3_HEALTH], player);
                break;
            case 2:
                drawFrame(canvas,sprites[INDEX_2_HEALTH], player);
                break;
            case 1:
                drawFrame(canvas,sprites[INDEX_1_HEALTH], player);
                break;
            case 0:
                drawFrame(canvas,sprites[INDEX_0_HEALTH], player);
                break;
            default:
                Log.e("PlayerAnimator.java","draw() - unexpected amount of health points");
        }
    }

    private void drawFrame(Canvas canvas, Sprite sprite, Player player){
        sprite.draw(
                canvas,
                (int)(player.getPositionX()),
                (int)(player.getPositionY()));
    }
}