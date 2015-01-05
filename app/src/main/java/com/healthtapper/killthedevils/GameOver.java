package com.healthtapper.killthedevils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.inmobi.monetization.IMBanner;

public class GameOver extends Activity {

    Button playagain;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
        playagain = (Button) findViewById(R.id.playagain);
        icon = (ImageView) findViewById(R.id.devil);
        icon.setBackgroundResource(R.drawable.icon);
        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.healthtapper.MAINACTIVITY");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   finish();
    }
}

