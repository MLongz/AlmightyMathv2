package com.example.mlong.allmigthymath;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity implements View.OnClickListener {
    GamePanel gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.add_score).setOnClickListener(this);
        findViewById(R.id.show_leaderboard).setOnClickListener(this);
        findViewById(R.id.show_achievement).setOnClickListener(this);
//
//        //turn of tittle
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //sett fullscreen
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        //setContentView(new GamePanel(this));
//        Intent intent = new Intent(this, SignInActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onSignInFailed() {
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
    }

    @Override
    public void onSignInSucceeded() {
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        gp = new GamePanel(this);
              setContentView(gp);
              gp.setActivity(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            beginUserInitiatedSignIn();
        }
        else if (view.getId() == R.id.add_score) {
            updateScore(100);
        }
        else if (view.getId() == R.id.sign_out_button) {
            signOut();
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
        else if (view.getId() == R.id.show_achievement){
            startActivityForResult(Games.Achievements.getAchievementsIntent(
                    getApiClient()), 1);
        }
        else if(view.getId() == R.id.show_leaderboard){
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                            getApiClient(), getString(R.string.leaderboard_id)), 2);
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
            Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_id), score);
            Games.Achievements.unlock(getApiClient(), getString(R.string.achievement_noob));
        }else {
            Toast.makeText(this,"Not logged in",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        gp.setPaused(true);
    }
}