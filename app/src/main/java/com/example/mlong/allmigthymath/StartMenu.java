package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Long Huynh on 18.06.2016.
 */
public class StartMenu extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;

    public StartMenu(Context context) {
        super.x = 130;
        super.y = 200;
        height = 400;
        width = 1654;
        spritesheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.startmenu);
        spritesheet = Bitmap.createScaledBitmap(spritesheet, width, height, true);
        this.context = context;


        //animation frames for the player image
        Bitmap[] image = new Bitmap[1];


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

    public void update() {
        animation.update();
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}

