package com.example.mlong.allmigthymath;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Long Huynh on 17.06.2016.
 */
public class Explosion extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private boolean exploded;

    public Explosion(int x, int y, Bitmap res, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        spritesheet = res;

        //animation frames for the player image
        Bitmap[] image = new Bitmap[numFrames];


        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(50);
    }

    public void update(){
       animation.update();
    }


    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
