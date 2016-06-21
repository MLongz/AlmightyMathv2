package com.simplemind.mlong.almigthymath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private StartMenuItems startGame, highScore, achievements, credit, creditScreen, picker, tomenu, gpslogin;
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
    private boolean rollCreditds;

    private int unlockedOutfit;

    private MainActivity mActivity;
    private List<Bitmap> cityList;





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
        cityList = new ArrayList<>();
        cityList.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.citiytest), 12288, GamePanel.HEIGTH, true));
        bg = new Background(getContext(), cityList.get(0));
        //count += 1;
        //create player image, width, height and how many frames
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
        //create start menu and menu-items
        startMenu = new StartMenu(getContext());
        picker = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.picker), 224, 379, 1, player.getX() + 40, 640);
        startGame = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.start2), 518, 100, 1, 710, 350);
        highScore = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.highscore2), 518, 100, 1, 420, 480);
        achievements = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.achievements2), 518, 100, 1, 980, 480);
        credit = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.credit), 518, 100, 1, 420, 610);
        creditScreen = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.creditsscreen), 1000, 568, 1, 1080, HEIGTH);
        creditScreen.setDy(5);
        gpslogin = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.googleplus), 518, 100, 1, 980, 610);
       //create all the unlockable outfits
        outfit1 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit1), 300, 300, 6);
        outfit1.setX(player.getX());
        outfit2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.lockedoutfit1), 300, 300, 6);
        outfit2.setX(outfit1.getX() + 400);
        outfit3 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.lockedplateroutfit2), 300, 300, 6);
        outfit3.setX(outfit2.getX() + 400);
        outfit4 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.lockedplateroutfit2), 300, 300, 6);
        outfit4.setX(outfit3.getX() + 400);
        //create the powerbar
        powerBar = new PowerBar(getContext(),
                BitmapFactory.decodeResource(getResources(), R.drawable.powerbar1), 300, 60, 5);
        //create vaiable that generate mathtasks
        mathTask = new MathTask (getContext(),
                BitmapFactory.decodeResource(getResources(), R.drawable.taskbubble), player.getX() + 100, player.getY() - 150, 200, 200);
        //create the braindead screen
        brainDead = new BrainDead(getContext());
        tomenu = new StartMenuItems(getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.menu), 518, 100, 1, 720, 770);

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
        rollCreditds = false;
        mActivity.setFirstTime(false);
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
        mathTask.MathAnswersList.clear();
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
        Rect rCredit = credit.getRectangle();
        Rect rDead = brainDead.getRectangle();
        Rect rToMenu = tomenu.getRectangle();
        Rect rGpsPlus = gpslogin.getRectangle();

        Rect rOutfit1 = outfit1.getRectangle();
        Rect rOutfit2 = outfit2.getRectangle();
        Rect rOutfit3 = outfit3.getRectangle();
        Rect rOutfit4 = outfit4.getRectangle();

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
                    boolean inn = mActivity.isSignedIn();
                    if(inn == false){
                        Toast.makeText(getContext(), "Not logged in, logg in to save score", Toast.LENGTH_LONG).show();
                    }
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
                if(rCredit.contains(touchX, touchY)){
                    rollCreditds = true;
                }
                if(rGpsPlus.contains(touchX, touchY)){
                    if(mActivity.isSignedIn()){
                        mActivity.gpSignOut();
                        Toast.makeText(getContext(), "Signed out, logg in to save score", Toast.LENGTH_LONG).show();
                    }else {
                        mActivity.gpSingIn();
                    }
                }
                if(rOutfit1.contains(touchX, touchY)){
                    picker.setX(outfit1.getX() + 40);
                    picker.setY(640);
                    changeOutfit(1);
                }
                if(rOutfit2.contains(touchX, touchY)){
                    picker.setX(outfit2.getX() + 40);
                    picker.setY(640);
                    if(unlockedOutfit >= 1){
                        changeOutfit(2);
                    }else {
                        picker.setX(outfit1.getX() + 40);
                        picker.setY(640);
                    }
                }
                if(rOutfit3.contains(touchX, touchY)){
                    picker.setX(outfit3.getX() + 70);
                    picker.setY(640);
                    if(unlockedOutfit >= 2){
                        changeOutfit(3);
                    }else{
                        picker.setX(outfit1.getX() + 40);
                        picker.setY(640);
                    }
                }
                if(rOutfit4.contains(touchX, touchY)){
                    picker.setX(outfit4.getX() + 70);
                    picker.setY(640);
                    if(unlockedOutfit == 3){
                        changeOutfit(4);
                    }else {
                        picker.setX(outfit1.getX() + 40);
                        picker.setY(640);
                    }
                }
            }
            if(player.isPlaying() == true && player.isAttack() == false){
                laserStartTime = System.nanoTime();
                laser = new Power(getContext(), BitmapFactory.decodeResource(getResources(),R.drawable.laser_red), 1725, 95, 11, EYEX, EYEY, touchX, touchY);
                player.setAttack(true);
                laser.soundStart();
                for(int i = 0; i < mathTask.MathAnswersList.size(); i ++){
                    Rect tempr = mathTask.MathAnswersList.get(i).getRectangle();
                    if(tempr.contains(touchX, touchY) && player.isPlaying() == true){
                        if(mathTask.getAnswer() == mathTask.MathAnswersList.get(i).getAnswer()){
                            explosionStartTime = System.nanoTime();
                            explosion = new Explosion(mathTask.MathAnswersList.get(i).getX(), mathTask.MathAnswersList.get(i).getY(),
                                    BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 336, 287, 8);
                            explosion.setExploded(true);
                            changePowerBar();
                            player.setScore(10);
                            mathTask.MathAnswersList.remove(i);
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

    public void changeOutfit(int nr){
        if(nr == 1){
            player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit1), 300, 300, 6);
        }
        if(nr == 2){
            player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit2), 300, 300, 6);
        }
        if(nr == 3){
            player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit3), 300, 300, 6);
        }
        if(nr == 4){
            player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
        }
    }

    public void changePowerBar(){
        if(SCORE >= 1000){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar4), 300, 60, 5);
        }
        if(SCORE >= 210 && SCORE <= 600){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar3), 300, 60, 5);
        }
        if(SCORE >= 110 && SCORE <= 200){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar2), 300, 60, 5);
        }
        if(SCORE >= 0 && SCORE <= 100){
            powerBar = new PowerBar(getContext(),  BitmapFactory.decodeResource(getResources(), R.drawable.powerbar1), 300, 60, 5);
        }
    }

    public void unlockOutfits(){
        if(unlockedOutfit == 3){
            outfit4 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit4), 300, 300, 6);
            outfit4.setX(outfit3.getX() + 400);
        }
        if(unlockedOutfit >= 2){
            outfit3 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit3), 300, 300, 6);
            outfit3.setX(outfit2.getX() + 400);
        }
        if(unlockedOutfit >= 1){
            outfit2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeroutfit2), 300, 300, 6);
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
            credit.update();
            gpslogin.update();
            bg.update();
            if(mActivity.isSignedIn()){
                gpslogin = new StartMenuItems(getContext(),
                        BitmapFactory.decodeResource(getResources(), R.drawable.signoutgps), 518, 100, 1, gpslogin.getX(), gpslogin.getY());
            }else{
                gpslogin = new StartMenuItems(getContext(),
                        BitmapFactory.decodeResource(getResources(), R.drawable.googleplus), 518, 100, 1, gpslogin.getX(), gpslogin.getY());
            }

            picker.update();
            outfit1.update();
            outfit2.update();
            outfit3.update();
            outfit4.update();
            if(rollCreditds == true){
                creditScreen.update();
                if(creditScreen.getY() < -HEIGTH){
                    rollCreditds = false;
                    creditScreen.setY(GamePanel.HEIGTH);
                }
            }
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
                if(elapsed > 530){
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
                    mathTask.MathAnswersList.clear();
                    if(SCORE >= 510){
                        DIFFICULTSPEED = 10;
                        mathTask.getHardMath();
                    }
                    if(SCORE >= 210 && SCORE <= 500){
                        DIFFICULTSPEED = 7;
                        mathTask.getMediumMath();
                    }
                    if(SCORE >= 110 && SCORE <= 200){
                        DIFFICULTSPEED = 5;
                        mathTask.getEasyMath2();
                    }
                    if(SCORE >= 0 && SCORE <= 100){
                        DIFFICULTSPEED = 5;
                        mathTask.getEasyMath();
                    }
                    getNewTask = false;
                }
            }
            if(mathTask.MathAnswersList.get(0).getX() ==  player.getX()){
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
                credit.draw(canvas);
                gpslogin.draw(canvas);

                picker.draw(canvas);
                outfit1.draw(canvas);
                outfit2.draw(canvas);
                outfit3.draw(canvas);
                outfit4.draw(canvas);
                if(rollCreditds == true){
                    creditScreen.draw(canvas);
                }
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
