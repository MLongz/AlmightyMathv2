package com.example.mlong.allmigthymath;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by M. Long on 06.06.2016.
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){
        image = Bitmap.createScaledBitmap(res, GamePanel.WIDTH, GamePanel.HEIGTH, true);
        dx = GamePanel.MOVESPEED;
    }

    public void update(){
        x += dx;
        if(x < -GamePanel.WIDTH){
            x = 0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        //draw a new image after the original one so it looks like a loop
        if(x < 0){
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }

}
