package com.example.mlong.allmigthymath;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by M. Long on 07.06.2016.
 */
public class MathAnswer extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;
    private int answer;

    public MathAnswer(Context context, int answer, int y, Bitmap res, int w, int h, int numFrames) {
        x = GamePanel.WIDTH ;
        dx = GamePanel.DIFFICULTSPEED;
        this.answer = answer;
        this.y = y;
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
        animation.setDelay(350);
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
        x -= dx;
        animation.update();
        if(x < -GamePanel.WIDTH){
            x = GamePanel.WIDTH;
        }
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), String.valueOf(answer), 255, 255, 255, 0, 30), x, y, null);
    }
}
