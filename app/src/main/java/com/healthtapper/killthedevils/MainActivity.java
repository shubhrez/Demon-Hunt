package com.healthtapper.killthedevils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class MainActivity extends Activity {

    GameView gameview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameview = new GameView(this);
        setContentView(gameview);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameview.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameview.resume();
    }

//    @Override
//    protected void onRestart() {
//        super.onStop();
//        Intent intent = new Intent("com.healthtapper.MAINACTIVITY");
//        startActivity(intent);
//    }
}
