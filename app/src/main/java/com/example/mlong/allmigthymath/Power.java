package com.example.mlong.allmigthymath;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Long Huynh on 16.06.2016.
 */
public class Power extends GameObject{
    private Bitmap spritesheet;
    private Animation animation = new Animation();
    float ta;

    public Power(Bitmap res, int w, int h, int numFrames, int x, int y, int targetx, int targety) {
        width = w;
        height = h;
        spritesheet = res;
        super.x = x;
        super.y = y;

        float angle = (float) Math.toDegrees(Math.atan2(targety - GamePanel.EYEY, targetx - GamePanel.EYEX));
        if(angle < 0){
            angle += 360;
        }

        //animation frames for the player image
        Bitmap[] image = new Bitmap[numFrames];
        //createBitmap(spritesheet, i * width, 0, width, height, matrix, true);

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }
        ta = angle;
        animation.setFrames(image);
        animation.setDelay(50);
    }

    public void draw(Canvas canvas){
        canvas.rotate(ta, GamePanel.EYEX, GamePanel.EYEY);
        canvas.drawBitmap(animation.getImage(), x, y, null);

    }

    public void update(){
        animation.update();
    }
}
