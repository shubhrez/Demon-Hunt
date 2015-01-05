package com.healthtapper.killthedevils;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FireBallSprite {

    private Bitmap bmp;

    public FireBallSprite(Bitmap bmp){
        this.bmp = bmp;
    }

    public void onDraw(Canvas canvas,float x,float y){
        canvas.drawBitmap(bmp, x,y, null);
    }
}
