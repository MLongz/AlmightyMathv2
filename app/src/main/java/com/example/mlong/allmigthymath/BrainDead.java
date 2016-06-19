package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by M. Long on 11.06.2016.
 */
public class BrainDead extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;

    public BrainDead(Context context) {
        super.x =  126;
        super.y = 120;
        height = 513;
        width = 1654;
        spritesheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
        spritesheet = Bitmap.createScaledBitmap(spritesheet, width, height, true);
        this.context = context;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[1];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);
    }


    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    public void update(){
        animation.update();
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), String.valueOf(GamePanel.SCORE), 225, 225, 225, 50, 1, 40), x, y, null);
    }


}
