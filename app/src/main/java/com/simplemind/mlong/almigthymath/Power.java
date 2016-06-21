package com.simplemind.mlong.almigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

/**
 * Created by Long Huynh on 16.06.2016.
 */
public class Power extends GameObject{
    private Bitmap spritesheet;
    private Animation animation = new Animation();
    private float ta;
    private final MediaPlayer mp;
    private int currentFrame;

    public Power(Context context, Bitmap res, int w, int h, int numFrames, int x, int y, int targetx, int targety) {
        width = w;
        height = h;
        spritesheet = res;
        super.x = x;
        super.y = y;
        super.frames = numFrames;
        mp = MediaPlayer.create(context, R.raw.laser2);
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
        animation.setDelay(20);
    }

    public void soundStart(){
        mp.start();
    }

    public void draw(Canvas canvas){
        canvas.rotate(ta, GamePanel.EYEX, GamePanel.EYEY);
        canvas.drawBitmap(animation.getImage(), x, y, null);

    }

    public int getCurrentFrame(){
        return currentFrame = animation.getFrame();
    }
    public void update(){
        animation.update();
    }
}
