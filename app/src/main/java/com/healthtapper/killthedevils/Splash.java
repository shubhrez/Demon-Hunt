package com.healthtapper.killthedevils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.splunk.mint.Mint;


public class Splash extends Activity {

    Button start,instructions;
    TextView title,highestscore;
    public static SharedPreferences pref;
    static String PREF_NAME = "HighestScore";
    public static final String HIGHESTSCORE = "highestscore";
    private static Integer highestScore = 0;
    private ImageView devil,angel,fireball;

   // private Prm prm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Mint.initAndStartSession(Splash.this, "fc0387ca");
        InMobi.initialize(Splash.this, "d37f4bc09bec4ca48cbcc6ae3afaa0ab");
        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
        
//        if(prm==null)
//            prm=new Prm(this, null, false);
//  //      AdView adView=(AdView)findViewById(R.id.myAdView);
//        AdView adView=new AdView(this, AdView.BANNER_TYPE_IN_APP_AD, AdView.PLACEMENT_TYPE_INTERSTITIAL, false, false,
//                AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Toxia_FRE.ttf");
        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        start = (Button) findViewById(R.id.start);
        instructions = (Button) findViewById(R.id.instructions);
        title = (TextView) findViewById(R.id.title);
        devil = (ImageView) findViewById(R.id.devil);
        devil.setBackgroundResource(R.drawable.icon);
        highestscore = (TextView) findViewById(R.id.highestscore);
        highestscore.setTypeface(custom_font);
        title.setTypeface(custom_font);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.healthtapper.MAINACTIVITY");
                startActivity(intent);
            }
        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.healthtapper.INSTRUCTIONS");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        highestScore = pref.getInt(HIGHESTSCORE, 0);
        String scoretext = String.valueOf(highestScore);
        highestscore.setText("Highest Score : " + scoretext);

    }
}
