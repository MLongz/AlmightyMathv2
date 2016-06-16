package com.example.mlong.allmigthymath;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by M. Long on 06.06.2016.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public static  int WIDTH = 1920;
    public static  int HEIGTH = 1080;
    public static  int MOVESPEED = -5;
    public static  int DIFFICULTSPEED = 10;
    public static int powerBarY = 0;
    public static int powerBarX = 0;
    public static int SCORE = 0;
    public static int EYEX = 0;
    public static int EYEY = 0;
    public static int touchX, touchY;

    private MainThread thread;
    private Background bg;
    private Player player;
    private MathTask mathTask;
    private PowerBar powerBar;
    private BrainDead brainDead;
    private Score score;
    private Power laser;
    private long laserStartTime;




    public GamePanel(Context context){
        super(context);

        //add the callback to the surfaceholder to interepts events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch (Exception e){}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //create background
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.city2));
        //create player image, width, height and how many frames
        player = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
        powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar1), 300, 60, 5); //TODO lag flere powerbar bilder
        mathTask = new MathTask (getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.taskbubble), player.getX() + 100, player.getY() - 150, 200, 200);
        brainDead = new BrainDead(getContext());

        EYEX = player.getX() + 220;
        EYEY = player.getY() + 20;

        laser =  new Power(BitmapFactory.decodeResource(getResources(),R.drawable.laser_red), 1725, 95, 11, EYEX, EYEY, 1700, 770);

        score = new Score(getContext());

        powerBarY = powerBar.getY();
        powerBarY = powerBar.getX();

        //we can safely create the game loop
        thread.setRunning(true);
        thread.start();
        mathTask.getEasyMath();
        player.setPlaying(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //create a rectangle from touch xy to check if it collide with answerbubble
        int x = (int) event.getX();
        int y = (int) event.getY();
        touchX = x;
        touchY = y;
        Rect touch = new Rect(x, y, 200, 200);
        Rect r = mathTask.getMathAnswerRect();
        Rect rDead = brainDead.getRectangle();
        player.setAttack(true);
        laserStartTime = System.nanoTime();
        laser = new Power(BitmapFactory.decodeResource(getResources(),R.drawable.laser_red), 1725, 95, 11, EYEX, EYEY, touchX, touchY);

//        if(event.getAction()==MotionEvent.ACTION_DOWN) {
//            if (!player.isPlaying()) {
//                mathTask.getEasyMath();
//                player.setPlaying(true);
//            }
//            player.setUp(true);
//            return true;
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP){
//            player.setUp(false);
//            return true;
//        }
        try{
            if(player.isPlaying() == true){
                for(int i = 0; i < mathTask.fakeAnswersList.size(); i ++){
                    Rect tempr = mathTask.fakeAnswersList.get(i).getRectangle();
                    if(tempr.contains(touch) && player.isPlaying() == true){
                        if(mathTask.getAnswer() == mathTask.fakeAnswersList.get(i).getAnswer()){
                            changePowerBar();
                            player.setScore(10);
                            mathTask.fakeAnswersList.clear();
                            if(SCORE >= 1010){
                                mathTask.getHardMath();
                                DIFFICULTSPEED = 10;
                            }
                            if(SCORE >= 510 && SCORE <= 1000){
                                mathTask.getHardMath();
                                DIFFICULTSPEED = 10;
                            }
                            if(SCORE >= 110 && SCORE <= 500){
                                mathTask.getMediumMath();
                                DIFFICULTSPEED = 10;

                            }
                            if(SCORE >= 0 && SCORE <= 100){
                                mathTask.getEasyMath();

                            }//TODO fiks if testen
                            mathTask.update();
                            player.setAttack(false);
                        }else {
                            player.setPlaying(false);

                        }
                    }
                }
            }else{
                player.resetScore();
                SCORE = player.getScore();
                DIFFICULTSPEED = 10;
                player = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit2), 300, 300, 7);
                player.setPlaying(true);
                mathTask.fakeAnswersList.clear();
                mathTask.getEasyMath();
            }
        }catch (Exception ex){
        }
        return super.onTouchEvent(event);
    }

    public void changePowerBar(){
        if(SCORE >= 1010){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar4), 300, 60, 5);
        }
        if(SCORE >= 410 && SCORE <= 600){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar3), 300, 60, 5);
            player = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
        }
        if(SCORE >= 210 && SCORE <= 400){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar2), 300, 60, 5);
            player = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit3), 300, 300, 7);
        }
        if(SCORE >= 0 && SCORE <= 200){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar1), 300, 60, 5);
        }
    }

    public void update() {
        SCORE = player.getScore();
        //update the background only if the payer is playing
        if(player.isPlaying()){
            bg.update();
            player.update();
            //mathTask.update();
            powerBar.update();
            laser.update();
            if(mathTask.fakeAnswersList.get(0).getX() <=  0){
                player.setPlaying(true);
            }
        }
        long elapsed = (System.nanoTime() - laserStartTime)/1000000;
        if(elapsed > 1000){
            player.setAttack(false);
            laserStartTime = System.nanoTime();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //get the screen scalefactor
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGTH*1.f);

        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            mathTask.draw(canvas);
            powerBar.draw(canvas);
            score.draw(canvas);
            if(player.isPlaying() == false){
                brainDead.draw(canvas);
            }
            if(player.isAttack() == true){
                laser.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }


}
