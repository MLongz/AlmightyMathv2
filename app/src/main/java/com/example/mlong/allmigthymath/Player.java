package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.zip.CheckedOutputStream;

/**
 * Created by M. Long on 06.06.2016.
 */
public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int x;
    private Context context;

    public Player(Context context, Bitmap res, int w, int h, int numFrames) {
        x = 100;
        y = 750;
        dy = 0;
        score = 0;
        width = w;
        height = h;
        spritesheet = res;
        this.context = context;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[numFrames];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(150);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b){
        up = b;
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
//        long elapsed = (System.nanoTime()-startTime)/1000000;
//        if (elapsed > 100){
//            score ++;
//            startTime = System.nanoTime();
//        }
        animation.update();
        //speed for up and down
//        if(up){
//            dy = (int) (dya -= 1.1);
//        }else{
//            dy = (int) (dya += 1.1);
//        }
//
//        //player speed
//        if(dy > 14) dy = 14;
//        if(dy <- 14) dy = -14;
//      //falling speed
//        y += dy*2;
//        dy = 0;
    }

    public void setScore(int score) {
        this.score += score;
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y , null);
    }

    public int getScore() {
        return score;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void resetDYA(){
        this.dya = 0;
    }

    public void resetScore(){
        this.score = 0;
    }
}
