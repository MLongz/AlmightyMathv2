package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by M. Long on 08.06.2016.
 */
public class PowerBar extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;

    public PowerBar(Context context, Bitmap res, int w, int h, int numFrames) {
        width = w;
        height = h;
        x = 10;
        y = 10;
        spritesheet = res;
        this.context = context;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[numFrames];


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
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), String.valueOf(""), 61, 61, 61, 0, 0, 20), x, y, null);
    }


}
