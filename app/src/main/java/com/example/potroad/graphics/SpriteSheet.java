package com.example.potroad.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.potroad.R;
import com.example.potroad.core.Player;
import com.example.potroad.panel.HealthBar;

public class SpriteSheet {
    private static final int SPRITE_WIDTH_IN_PIXELS = 100;
    private static final int SPRITE_HEIGHT_IN_PIXELS = 100;
    private final Bitmap bitmap;

    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pot_road_sprite_sheet, bitmapOptions);
    }


    public Sprite[] getPlayerSprites(Player player){
        Sprite[] sprites = new Sprite[4];

        int playerWidth = (int)player.getWidth();
        int playerHeight = (int)player.getHeight();

        sprites[0] = new Sprite(this, new Rect(0, 0, 100, 100),playerWidth,playerHeight);
        sprites[1] = new Sprite(this, new Rect(500,0,600,100),playerWidth,playerHeight);
        sprites[2] = new Sprite(this, new Rect(600,0,700,100),playerWidth,playerHeight);
        sprites[3] = new Sprite(this, new Rect(700, 0, 800, 100),playerWidth,playerHeight);

        return sprites;
    }

    public Sprite getPotholeSprite(int potholeWidth, int potholeHeight){
        int i = (int)(Math.random() * 3);

        switch (i){
            case 0:
                return new Sprite(this, new Rect(100, 0, 200, 100),potholeWidth,potholeHeight);
            case 1:
                return new Sprite(this, new Rect(200, 0, 300, 100),potholeWidth,potholeHeight);
            case 2:
                return new Sprite(this, new Rect(300, 0, 400, 100),potholeWidth,potholeHeight);
            default:
                Log.e("SpriteSheet.java","getPotholeSprite() - unexpected id");
                return null;
        }
    }

    public Sprite getHealthSprite(){
        return new Sprite(
                this,
                new Rect(400, 0, 500, 100),
                HealthBar.ICON_SIZE,
                HealthBar.ICON_SIZE);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
