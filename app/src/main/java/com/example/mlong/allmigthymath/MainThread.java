package com.example.mlong.allmigthymath;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by M. Long on 06.06.2016.
 */
public class MainThread extends Thread {
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime;
        long timeMilis;
        long waitTime;
        long totalTime = 0;
        int framCount = 0;
        long targetTime = 1000/FPS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;

            //try locking canvas for pixel editing
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception e){}
            finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){}
                }
            }
            timeMilis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime-timeMilis;

            try{
                this.sleep(waitTime);
            }catch (Exception e){
                totalTime += System.nanoTime()-startTime;
                framCount++;
                if(framCount == FPS){
                    averageFPS = 1000/((totalTime/framCount)/1000000);
                    framCount = 0;
                    totalTime = 0;
                    System.out.println(averageFPS);
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
