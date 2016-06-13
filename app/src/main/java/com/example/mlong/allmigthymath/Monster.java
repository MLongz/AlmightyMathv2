package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.text.DecimalFormat;

/**
 * Created by M. Long on 10.06.2016.
 */
public class Monster extends GameObject {
    private Bitmap spritesheet;
    Animation animation = new Animation();
    private Context context;

    private String answerString;

    public Monster(Context context, double answer, int y, Bitmap res, int w, int h, int numFrames) {
        x = GamePanel.WIDTH ;
        dx = GamePanel.DIFFICULTSPEED;
        super.answer = answer;
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

        DecimalFormat df = new DecimalFormat("#.##");
        String temp = df.format(answer);
        if(temp.contains(".0")){
            temp = temp.substring(0,1);
            answerString = temp;
        }else {
            answerString = temp;
        }
        animation.setFrames(image);
        animation.setDelay(850);
    }

    public void update(){
        x -= dx;
        animation.update();
        if(x < -GamePanel.WIDTH){
            x = GamePanel.WIDTH;
        }
    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(drawTextToBitmap(context, animation.getImage(), answerString, 255, 255, 255, 0, 30), x, y, null);
    }
}
