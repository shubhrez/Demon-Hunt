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

public class Instructions extends Activity {

    Button start;
    TextView guide1,guide2,guide3,guide4,guide5;
    ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        IMBanner banner = (IMBanner) findViewById(R.id.banner);
        banner.loadBanner();
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Toxia_FRE.ttf");
        start = (Button) findViewById(R.id.start);
        icon = (ImageView) findViewById(R.id.icon);
        icon.setBackgroundResource(R.drawable.icon);
        guide1 = (TextView) findViewById(R.id.guide1);
        guide2 = (TextView) findViewById(R.id.guide2);
        guide3 = (TextView) findViewById(R.id.guide3);
        guide4 = (TextView) findViewById(R.id.guide4);
        guide5 = (TextView) findViewById(R.id.guide5);
        guide1.setTypeface(custom_font);
        guide2.setTypeface(custom_font);
        guide3.setTypeface(custom_font);
        guide4.setTypeface(custom_font);
        guide5.setTypeface(custom_font);
        start.setOnClickListener(new View.OnClickListener() {
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
        finish();
    }
}
