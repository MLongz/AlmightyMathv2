package com.simplemind.mlong.almigthymath;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by M. Long on 06.06.2016.
 */
public class Player extends GameObject{
    private int score;
    private boolean playing;
    private boolean attack;
    private Animation animation = new Animation();

    public Player(Bitmap res, int w, int h, int numFrames) {
        super.x = 140;
        super.y = 640;
        score = 0;
        width = w;
        height = h;
        super.bitmap = res;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[numFrames];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(bitmap, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(70);
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }


    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        super.setBitmap(bitmap);
    }

    public void update(){
        animation.update();
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


    public void resetScore(){
        this.score = 0;
    }
}
