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
    public static  int DIFFICULTSPEED = 5;
    public static int powerBarY = 0;
    public static int powerBarX = 0;
    public static int SCORE = 0;
    public static int EYEX = 0;
    public static int EYEY = 0;
    public static int touchX, touchY;


    private MainThread thread;
    private Background bg;
    private StartMenu startMenu;
    private StartMenuItems startGame, highScore, achievements, options, continuegame, tomenu;
    private Player player, outfit1, outfit2, outfit3, outfit4;
    private MathTask mathTask;
    private PowerBar powerBar;
    private BrainDead brainDead;
    private Score score;
    private Power laser;
    private Explosion explosion;

    private long laserStartTime;
    private long explosionStartTime;
    private long monsterDisapearStartTime;

    private boolean showMenu;
    private boolean getNewTask;
    private boolean isDead;

    private int unlockedOutfit;

    private MainActivity mActivity;





    public GamePanel(Context context){
        super(context);

        //add the callback to the surfaceholder to interepts events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);

    }
    public void setActivity(MainActivity overlayActivity) {
        mActivity = overlayActivity;
    }
    public void setUnlockedOutfit(int unlockedOutfit) {
        this.unlockedOutfit = unlockedOutfit;
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
                bg.soundPause();
            }catch (Exception e){}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //create background
        bg = new Background(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.city2));
        //create start menu and menu-items
        startMenu = new StartMenu(getContext());
        startGame = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.start2), 406, 65, 1, 750, 350);
        highScore = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.highscore2), 406, 65, 1, 750, 460);
        achievements = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.achievements2), 518, 62, 1, 700, 570);
        options = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.options2), 406, 65, 1, 750, 680);
        //create player image, width, height and how many frames
        player = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
        //create all the unlockable outfits
        outfit1 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit1), 300, 300, 6);
        outfit1.setX(player.getX());
        outfit2 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.lockedoutfit1), 300, 300, 6);
        outfit2.setX(outfit1.getX() + 400);
        outfit3 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.lockedplateroutfit2), 300, 300, 6);
        outfit3.setX(outfit2.getX() + 400);
        outfit4 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.lockedplateroutfit2), 300, 300, 6);
        outfit4.setX(outfit3.getX() + 400);
        //create the powerbar
        powerBar = new PowerBar(getContext(),
                BitmapFactory.decodeResource(getResources(), R.drawable.powerbar1), 300, 60, 5); //TODO lag flere powerbar bilder
        //create vaiable that generate mathtasks
        mathTask = new MathTask (getContext(),
                BitmapFactory.decodeResource(getResources(), R.drawable.taskbubble), player.getX() + 100, player.getY() - 150, 200, 200);
        //create the braindead screen
        brainDead = new BrainDead(getContext());
        tomenu = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.menu), 518, 62, 1, 720, 770);

        //get the player eyes xy
        EYEX = player.getX() + 240;
        EYEY = player.getY() + 20;
        //create the laser and explosion
        laser = new Power(getContext(),
                BitmapFactory.decodeResource(getResources(),R.drawable.laser_red), 1725, 95, 11, EYEX, EYEY, touchX, touchY);
        explosion = new Explosion(0, 0,
                BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 336, 287, 8);
        //create the score screen
        score = new Score(getContext());

        powerBarY = powerBar.getY();
        powerBarY = powerBar.getX();
        //turn off/on all the necessary variables before games start
        mathTask.getEasyMath();
        player.setPlaying(false);
        player.setAttack(false);
        explosion.setExploded(false);
        showMenu = true;
        getNewTask = false;
        isDead = false;
        unlockOutfits();
        //we can safely create the game loop
        thread.setRunning(true);
        thread.start();
    }

    public void newGame(){
        player.resetScore();
        SCORE = player.getScore();
        DIFFICULTSPEED = 5;
        isDead = false;
        player.setPlaying(true);
        showMenu = false;
        mathTask.fakeAnswersList.clear();
        mathTask.getEasyMath();
        bg.soundStart();
        unlockOutfits();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //create a rectangle from touch xy to check if it collide with answerbubble
        touchX  = (int) event.getX();
        touchY  = (int) event.getY();

        //Rect touch = new Rect(x, y, 200, 200);
        Rect rStartGame = startGame.getRectangle();
        Rect rHighScore = highScore.getRectangle();
        Rect rAchiements = achievements.getRectangle();
        Rect rOption = options.getRectangle();
        Rect rDead = brainDead.getRectangle();
        Rect rToMenu = tomenu.getRectangle();
//        explosionStartTime = System.nanoTime();
//        explosion = new Explosion(touchX,touchY,
//                BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 336, 287, 8);
//        explosion.setExploded(true);

        try{
            if(isDead == true){
                if(rDead.contains(touchX, touchY)){
                    newGame();
                }
                if(rToMenu.contains(touchX, touchY)){
                    player.setPlaying(false);
                    isDead = false;
                    showMenu = true;
                }
            }
            if(showMenu == true){
                if(rStartGame.contains(touchX, touchY)){
                    player.setPlaying(true);
                    showMenu = false;
                    newGame();
                }
                if(rHighScore.contains(touchX, touchY)){
                    mActivity.showLeaderboard();
                    showMenu = true;
                }
                if(rAchiements.contains(touchX, touchY)){
                    mActivity.showAchievements();
                    showMenu = true;
                }
                if(rOption.contains(touchX, touchY)){
                    showMenu = true;
                }
            }
            if(player.isPlaying() == true){
                laserStartTime = System.nanoTime();
                laser = new Power(getContext(), BitmapFactory.decodeResource(getResources(),R.drawable.laser_red), 1725, 95, 11, EYEX, EYEY, touchX, touchY);
                player.setAttack(true);
                laser.soundStart();
                for(int i = 0; i < mathTask.fakeAnswersList.size(); i ++){
                    Rect tempr = mathTask.fakeAnswersList.get(i).getRectangle();
                    if(tempr.contains(touchX, touchY) && player.isPlaying() == true){
                        if(mathTask.getAnswer() == mathTask.fakeAnswersList.get(i).getAnswer()){
                            explosionStartTime = System.nanoTime();
                            explosion = new Explosion(mathTask.fakeAnswersList.get(i).getX(), mathTask.fakeAnswersList.get(i).getY(),
                                    BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 336, 287, 8);
                            explosion.setExploded(true);
                            changePowerBar();
                            player.setScore(10);
                            mathTask.fakeAnswersList.remove(i);
                            monsterDisapearStartTime = System.nanoTime();
                            getNewTask = true;
                        }else{
                            mActivity.updateScore(player.getScore());
                            isDead = true;
                            player.setPlaying(false);
                        }
                    }
                }
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

    public void unlockOutfits(){
        if(unlockedOutfit == 3){
            outfit3 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit3), 300, 300, 6);
            outfit3.setX(outfit2.getX() + 400);
            outfit4 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
            outfit4.setX(outfit3.getX() + 400);
        }
        if(unlockedOutfit == 2){
            outfit3 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit3), 300, 300, 6);
            outfit3.setX(outfit2.getX() + 400);
            outfit2 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit2), 300, 300, 6);
            outfit2.setX(outfit1.getX() + 400);
        }
        if(unlockedOutfit == 1){
            outfit2 = new Player(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit2), 300, 300, 6);
            outfit2.setX(outfit1.getX() + 400);
        }
    }

    public void update() {
        SCORE = player.getScore();
        if(showMenu == true || player.isPlaying() == false){
            startMenu.update();
            startGame.update();
            highScore.update();
            achievements.update();
            options.update();

            bg.update();

            outfit1.update();
            outfit2.update();
            outfit3.update();
            outfit4.update();
        }
        if(isDead == true){
            player.setPlaying(false);
            tomenu.update();
        }
        if(player.isPlaying()){
            bg.update();
            player.update();
            powerBar.update();
            mathTask.update(0);
            if(player.isAttack() == true){
                laser.update();
                long elapsed = (System.nanoTime() - laserStartTime)/1000000;
                if(elapsed > 500){
                    player.setAttack(false);
                }
            }
            if(explosion.isExploded() == true){
                explosion.update();
                long elapsed2 = (System.nanoTime() - explosionStartTime)/1000000;
                if(elapsed2 > 500){
                    explosion.setExploded(false);
                }
            }
            if(getNewTask == true){
                mathTask.update(30);
                long elapsed3 = (System.nanoTime() - monsterDisapearStartTime)/1000000;
                if(elapsed3 > 1000){
                    mathTask.fakeAnswersList.clear();
                    if(SCORE >= 1010){
                        DIFFICULTSPEED = 10;
                        //mathTask.getHardMath();
                        mathTask.getEasyMath();
                    }
                    if(SCORE >= 510 && SCORE <= 1000){
                        DIFFICULTSPEED = 7;
                        //mathTask.getHardMath();
                        mathTask.getEasyMath();
                    }
                    if(SCORE >= 110 && SCORE <= 500){
                        DIFFICULTSPEED = 5;
                        //mathTask.getMediumMath();
                        mathTask.getEasyMath();
                    }
                    if(SCORE >= 0 && SCORE <= 100){
                        DIFFICULTSPEED = 5;
                        mathTask.getEasyMath();
                    }
                    getNewTask = false;
                }
            }
            if(mathTask.fakeAnswersList.get(0).getX() <=  0){
                isDead = true;
            }
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

            if(player.isPlaying() == true){
                mathTask.draw(canvas);
                powerBar.draw(canvas);
                score.draw(canvas);
                player.draw(canvas);
            }
            if(player.isPlaying() == false && showMenu == false){
                outfit1.draw(canvas);
                outfit2.draw(canvas);
                outfit3.draw(canvas);
                outfit4.draw(canvas);
            }
            if(isDead == true && player.isPlaying() == false){
                brainDead.draw(canvas);
                tomenu.draw(canvas);
            }
            if(showMenu == true){
                startMenu.draw(canvas);
                startGame.draw(canvas);
                highScore.draw(canvas);
                achievements.draw(canvas);
                options.draw(canvas);

                outfit1.draw(canvas);
                outfit2.draw(canvas);
                outfit3.draw(canvas);
                outfit4.draw(canvas);
            }
            if(player.isAttack() == true){
                laser.draw(canvas);
            }
            if(explosion.isExploded() == true){
                explosion.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }


}
