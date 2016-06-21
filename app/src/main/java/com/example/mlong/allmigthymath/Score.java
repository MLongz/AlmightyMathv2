package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by M. Long on 12.06.2016.
 */
public class Score extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;

    public Score(Context context) {
        width = 300;
        height = 90;
        super.x = GamePanel.powerBarX + 5;
        super.y = 90;
        spritesheet =  BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
        this.context = context;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[1];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);
    }


    public void update(){
        animation.update();
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), String.valueOf(": " + GamePanel.SCORE), 255, 255, 255, 30, 0, 20), x, y, null);
    }


}
