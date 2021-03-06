package com.simplemind.mlong.almigthymath;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

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
    private List<Integer> easyNumberList;
    private List<BitmapMonster> bitmapGroundMonsters, bitmapFlyingMonsters;

    public List<Monster> MathAnswersList;




    public MathTask( Context context, Bitmap res, int x, int y, int w, int h ){
        MathAnswersList = new ArrayList<>();

        bitmapGroundMonsters = new ArrayList<>();
        bitmapGroundMonsters.add( new BitmapMonster(BitmapFactory.decodeResource(context.getResources(), R.drawable.frog), 224, 224, 3));
        bitmapGroundMonsters.add( new BitmapMonster(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeleton), 224, 224, 4));

        bitmapFlyingMonsters = new ArrayList<>();
        bitmapFlyingMonsters.add( new BitmapMonster(BitmapFactory.decodeResource(context.getResources(), R.drawable.bat), 224, 224, 3));
        bitmapFlyingMonsters.add( new BitmapMonster(BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost), 224, 224, 3));

        yList = new ArrayList<>();
        yList.add(20);
        yList.add(250);
        yList.add(500);
        yList.add(710);

        easyNumberList = new ArrayList<>();
        easyNumberList.add(2);
        easyNumberList.add(4);
        easyNumberList.add(6);
        easyNumberList.add(8);
        easyNumberList.add(10);

        super.x = x;
        super.y = y;
        super.width = w;
        super.height = h;
        this.context = context;
        this.res = res;
        image = res.createScaledBitmap(res, height, width, true);




    }

    public Rect getMathAnswerRect(){
        return rectAnswer = MathAnswersList.get(0).getRectangle();
    }

    public void update(int y){
        brain.update();
        for(int i  = 0; i < MathAnswersList.size(); i++){
            MathAnswersList.get(i).update(y);
            MathAnswersList.get(i).setDx(+randomGenerator.nextInt(5));
        }
    }

    public void getEasyMath(){
        this.mathX = randomGenerator.nextInt(10) + 1;
        this.mathY = randomGenerator.nextInt(10) + 1;
        int rndTask = randomGenerator.nextInt(3) + 1;
        if(rndTask == 3){
            this.mathX = easyNumberList.get(randomGenerator.nextInt(easyNumberList.size()));
            this.mathY = 2;
            createMathtask(mathX, mathY, rndTask, 2);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain2), x - 10, y - 50, 250, 250, 2);
        }else {
            createMathtask(mathX, mathY, rndTask, 2);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain2), x - 10, y - 50, 250, 250, 2);
        }
    }

    public void getEasyMath2(){
        this.mathX = randomGenerator.nextInt(50) + 1;
        this.mathY = randomGenerator.nextInt(10) + 1;
        int rndTask = randomGenerator.nextInt(3) + 1;
        if(rndTask == 3){
            this.mathX = easyNumberList.get(randomGenerator.nextInt(easyNumberList.size()));
            this.mathY = 2;
            createMathtask(mathX * 10, mathY * 10, rndTask, 2);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain3), x - 50, y - 130, 350, 350, 2);
        }else {
            createMathtask(mathX, mathY, rndTask, 2);
            int intx = (int) mathX;
            int inty = (int) mathY;
            string = intx + " " + this.mathSymbol + " " + inty;
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain3), x - 50, y - 130, 350, 350, 2);
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
            brain = new Brain(context, string, BitmapFactory.decodeResource(context.getResources(), R.drawable.brain3), x - 50, y - 130, 350, 350, 5);
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
                createRndMonster(rndY, answer);
                yListTemp.remove(p);
            }else {
                createRndMonster(rndY, answer + (MathAnswersList.size() + randomGenerator.nextInt(10)));
                yListTemp.remove(p);
            }

        }

    }

    public void createRndMonster(int rndY, double answer){
        if(rndY == 710){
            int rndGroundMonster = randomGenerator.nextInt(bitmapGroundMonsters.size());
            Bitmap bitmap = bitmapGroundMonsters.get(rndGroundMonster).getBitmap();
            int w = bitmapGroundMonsters.get(rndGroundMonster).getWidth();
            int h = bitmapGroundMonsters.get(rndGroundMonster).getHeight();
            int f = bitmapGroundMonsters.get(rndGroundMonster).getNumFrames();
            MathAnswersList.add(new Monster(context, answer, rndY, bitmap, w, h, f));
        }else {
            int rndGroundMonster = randomGenerator.nextInt(bitmapFlyingMonsters.size());
            Bitmap bitmap = bitmapFlyingMonsters.get(rndGroundMonster).getBitmap();
            int w = bitmapFlyingMonsters.get(rndGroundMonster).getWidth();
            int h = bitmapFlyingMonsters.get(rndGroundMonster).getHeight();
            int f = bitmapFlyingMonsters.get(rndGroundMonster).getNumFrames();
            MathAnswersList.add(new Monster(context, answer, rndY, bitmap, w, h, f));
        }
    }

    public void draw(Canvas canvas){
        brain.draw(canvas);
        for(int i  = 0; i < MathAnswersList.size(); i++){
            MathAnswersList.get(i).draw(canvas);
        }
    }
}
