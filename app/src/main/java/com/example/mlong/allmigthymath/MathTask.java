package com.example.mlong.allmigthymath;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by M. Long on 06.06.2016.
 */
public class MathTask extends GameObject{
    private Bitmap image, res;
    private double mathX, mathY, numFrames;
    private String mathSymbol;
    private Context context;
    private Random randomGenerator = new Random();
    private List<Integer> yList;
    private String string;
    private Brain brain;
    private Rect rectAnswer;
    public List<Monster> fakeAnswersList;


    public MathTask( Context context, Bitmap res, int x, int y, int w, int h ){
        fakeAnswersList = new ArrayList<>();
        yList = new ArrayList<>();
        //add fixed y
        yList.add(20);
        yList.add(270);
        yList.add(520);
        yList.add(770);

        super.x = x;
        super.y = y;
        super.width = w;
        super.height = h;
        this.context = context;
        this.res = res;
        image = res.createScaledBitmap(res, height, width, true);




    }

    public Rect getMathAnswerRect(){
        return rectAnswer = fakeAnswersList.get(0).getRectangle();
    }
    public void update(){
        brain.update();
        //generateAnswer.update();
        for(int i  = 0; i < fakeAnswersList.size(); i++){
         fakeAnswersList.get(i).update();
        }

    }

    public void getEasyMath(){
        this.mathX = randomGenerator.nextInt(10) + 1;
        this.mathY = randomGenerator.nextInt(10) + 1;
        int rndTask = 3;
        if(mathX < mathY && rndTask == 3){
            getEasyMath();
        }else {
            createMathtask(mathX, mathY, rndTask, 2);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain), x, y, 200, 200, 2);
        }
    }

    public void getMediumMath(){
        this.mathX = randomGenerator.nextInt(100) + 1;
        this.mathY = randomGenerator.nextInt(10) + 1;
        int rndTask = randomGenerator.nextInt(3) + 1;
        if(mathX < mathY && rndTask == 3){
            getMediumMath();
        }else {
            createMathtask(mathX, mathY, rndTask, 3);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain2), x - 10, y - 50, 250, 250, 5);
        }
    }

    public void getHardMath(){
        this.mathX = randomGenerator.nextInt(1000) + 1;
        this.mathY = randomGenerator.nextInt(1000) + 1;
        int rndTask = randomGenerator.nextInt(3) + 1;
        if(mathX < mathY && rndTask == 3){
            getHardMath();
        }else {
            createMathtask(mathX, mathY, rndTask, 4);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain3), x - 50, y - 130, 350, 350, 5);
        }
    }

    public void createMathtask(double mathX, double mathY, int rndTask, int difficult){
        this.mathX = mathX;
        this.mathY = mathY;
        if(rndTask == 0){
            this.answer = this.mathX + this.mathY;
            this.mathSymbol = "+";
        }
        if(rndTask == 1){
            this.answer = this.mathX - this.mathY;
            this.mathSymbol = "-";
        }
        if(rndTask == 2){
            this.answer = this.mathX * this.mathY;
            this.mathSymbol = "*";
        }
        if(rndTask == 3){
            this.answer = this.mathX / this.mathY;
            this.mathSymbol = "/";
        }
        //generate random y from fixed y list
        List<Integer> yListTemp = new ArrayList<>(yList);
        for(int i = 0; i < 4; i++){//TODO fiks height
            int p = randomGenerator.nextInt(yListTemp.size());
            int rndY = yListTemp.get(p);
            if(i == 0){
                fakeAnswersList.add(new Monster(context, answer, rndY, BitmapFactory.decodeResource(context.getResources(), R.drawable.monster1), 224, 224, 2));
                yListTemp.remove(p);
            }else {
                fakeAnswersList.add(new Monster(context, answer + (fakeAnswersList.size() + randomGenerator.nextInt(10)), rndY, BitmapFactory.decodeResource(context.getResources(), R.drawable.monster1), 224, 224, 2));
                yListTemp.remove(p);
            }

        }

    }

    public void draw(Canvas canvas){
        //String string = this.mathX + " " + this.mathSymbol + " " + this.mathY;
        //canvas.drawBitmap(drawTextToBitmap(context, image, string, 61, 61, 61, 0), x, y, null);
        brain.draw(canvas);
        //generateAnswer.draw(canvas);
        for(int i  = 0; i < fakeAnswersList.size(); i++){
            fakeAnswersList.get(i).draw(canvas);
        }
    }
}
