package com.simplemind.mlong.almigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Long Huynh on 18.06.2016.
 */
public class StartMenuItems extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private int numFrames;
    private Context context;

    public StartMenuItems(Context context, Bitmap bitmap, int width, int height, int numFrames, int x, int y) {
        super.x = x;
        super.y = y;
        super.height = height;
        super.width = width;
        spritesheet = bitmap;
        spritesheet = Bitmap.createScaledBitmap(spritesheet, width, height, true);
        this.context = context;
        this.numFrames = numFrames;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[1];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100);
    }

    public void update() {
        animation.update();
        y -= dy;
    }

    public void drawWithTxt(Canvas canvas, String s){
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), s, 225, 225, 225, 80, 0, 40), x, y, null);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
