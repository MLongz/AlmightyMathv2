package com.simplemind.mlong.almigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;


/**
 * Created by M. Long on 06.06.2016.
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;
    private final MediaPlayer mp;


    public Background(Context context, Bitmap bitmap){
        dx = GamePanel.MOVESPEED;
        mp = MediaPlayer.create(context, R.raw.canvai_sunset);
        image = bitmap;
    }

    public void update(){
        soundStart();
        x += dx;
        if(x < -12288){
            x = 0;
        }
    }

    public void soundStart(){
        mp.start();
    }

    public void soundPause(){
        mp.pause();
    }

    public void soundStop(){
        mp.stop();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        //draw a new image after the original one so it looks like a loop
        if(x < 0){
            canvas.drawBitmap(image, x + 12288, y, null);
        }
    }

}
