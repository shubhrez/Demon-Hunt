package com.healthtapper.killthedevils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmobi.monetization.IMBanner;

public class GameOver extends Activity {

    Button playagain;
    ImageView icon;
    TextView score,gameover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Toxia_FRE.ttf");
        Bundle bundle = getIntent().getExtras();
         //Extract the data…
        int value = bundle.getInt("SCORE");

        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
        score = (TextView) findViewById(R.id.score);
        gameover = (TextView) findViewById(R.id.gameover);
        playagain = (Button) findViewById(R.id.playagain);
        icon = (ImageView) findViewById(R.id.devil);
        icon.setBackgroundResource(R.drawable.icon);
//        new StringBuilder().append("Score : ").append(value).toString()
//        String score = "Score" + Integer.toString(score);
        score.setText(new StringBuilder().append("Score : ").append(value).toString());
        score.setTypeface(custom_font);
        gameover.setTypeface(custom_font);
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

