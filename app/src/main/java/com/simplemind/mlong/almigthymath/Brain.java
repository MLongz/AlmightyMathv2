package com.simplemind.mlong.almigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by M. Long on 10.06.2016.
 */
public class Brain extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;
    private String string;

    public Brain(Context context, String string, Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.string = string;
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        this.context = context;
        spritesheet = res;


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
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), string, 255, 255, 255, 0, 0, 20), x, y, null);
    }


}
