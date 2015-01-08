package com.healthtapper.killthedevils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Life {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right

    private static final int MAX_SPEED = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;

    private int width;
    private int height;

    public Life(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        x = rnd.nextInt(300) + 1;
        y = rnd.nextInt(250) + 80;
        xSpeed = ((rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED -10)) + 1;
        ySpeed = ((rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED -10)) + 1;
        if(ySpeed == 0) {
            ySpeed = 5;
        }

        if(xSpeed == 0) {
            xSpeed = 5;
        }
    }

    private void update() {
        if (x >= gameView.getWidth() - width  || x  <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - 300 || y  <= 80) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;

    }


    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }


    public boolean isCollision(float x2, float y2) {
        return x2 > x  && x2 < x + width  && y2 > y  && y2 < y + height ;
    }
}