package com.example.mlong.allmigthymath;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.GameHelper;

public class MainActivity extends BaseGameActivity{
    private GamePanel gp;
    private GameHelper gameHelper;
    private boolean firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gp = new GamePanel(this);
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setup(this);
        firstTime = true;
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
        if(firstTime == true){
            loadScoreOfLeaderBoard();
            gp.setActivity(this);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setContentView(gp);
                    Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_baby));
                }
            }, 2000);
        }
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void gpSignOut(){
        signOut();
    }

    public void gpSingIn(){
        beginUserInitiatedSignIn();
    }

    public boolean isSignedIn(){
        if(getApiClient().isConnected()){
            return true;
        }else {
            return false;
        }
    }

    public void showAchievements(){
        if(getApiClient().isConnected()){
            startActivityForResult(Games.Achievements.getAchievementsIntent(
                    getApiClient()), 1);
        }else {
            Toast.makeText(this,"Not logged in",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void showLeaderboard(){
        if(getApiClient().isConnected()){
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                    getApiClient(), getString(R.string.leaderboard_id)), 2);
        }else {
            Toast.makeText(this,"Not logged in",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateScore(int score){
        if(getApiClient().isConnected()){
            if(score > 500){
                Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_almighty));
            }
            if(score >= 310 && score <= 400 || score > 410){
                Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_super_iq));
            }
            if(score >= 210 && score <= 300 || score > 310){
                Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_smartass));
            }
            if(score >= 110 && score <= 200 || score > 210){
                Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_casual_player));
            }
            if(score >= 0 && score <= 100 || score > 110){
                Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_noob));
            }
            Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_id), score);
        }else {
            Toast.makeText(this,"Not logged in",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void loadScoreOfLeaderBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(),
                getString(R.string.leaderboard_id),
                LeaderboardVariant.TIME_SPAN_ALL_TIME,
                LeaderboardVariant.COLLECTION_SOCIAL).setResultCallback(
                new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
                    @Override
                    public void onResult(Leaderboards.LoadPlayerScoreResult arg0) {
                        if(arg0.getScore() != null){
                            LeaderboardScore c = arg0.getScore();
                            int playerScore = (int) c.getRawScore();
                            if(playerScore >= 310){
                                gp.setUnlockedOutfit(3);
                            }
                            if(playerScore >= 210 && playerScore <= 300){
                                gp.setUnlockedOutfit(2);
                            }
                            if(playerScore >= 110 && playerScore <= 200){
                                gp.setUnlockedOutfit(1);
                            }
                        }
                    }
                });
    }

}