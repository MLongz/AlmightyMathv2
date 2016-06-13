package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by M. Long on 08.06.2016.
 */
public class MathFakeAnswer extends GameObject {
    private Bitmap image;
    private Context context;
    private Random randomGenerator = new Random();
    int answer;

    public MathFakeAnswer(Context context, Bitmap res, int answer, int x, int y){
        width = 200;
        height = 200;
        super.x = GamePanel.WIDTH + x;
        dx = GamePanel.DIFFICULTSPEED;
        this.answer = answer;
        this.y = y;
        image = res.createScaledBitmap(res, width, height, true);

        this.context = context;


    }


    public void update(){
        x -= dx;
        if(x < -GamePanel.WIDTH){
            x = GamePanel.WIDTH;
        }
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void reset(){
        setX(GamePanel.WIDTH + 100);
        setY(randomGenerator.nextInt(GamePanel.HEIGTH - 200));

    }



    public void draw(Canvas canvas){
            String string = "= " + String.valueOf(answer);
            canvas.drawBitmap(drawTextToBitmap(context, image, string, 61, 61, 61, 0, 0), super.x, super.y, null);

    }
}
