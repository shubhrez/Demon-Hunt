package com.healthtapper.killthedevils;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private GameView gameView;
    private Bitmap bmp;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        x = rnd.nextInt(300) + 1;
        y = rnd.nextInt(250) + 80;
        xSpeed = ((rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED -10))*3/2 + 1;
        ySpeed = ((rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED -10))*3/2 + 1;
        if(ySpeed == 0) {
            ySpeed = 5;
        }

        if(xSpeed == 0) {
            xSpeed = 5;
        }
    }

//    private void update() {
//        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
//            xSpeed = -xSpeed;
//        }
//        x = x + xSpeed;
//        if (y >= gameView.getHeight() - height - ySpeed - 300 || y + ySpeed <= 80) {
//            ySpeed = -ySpeed;
//        }
//        y = y + ySpeed;
//        currentFrame = ++currentFrame % BMP_COLUMNS;
//    }

    private void update(int speedfactor) {
        if (x >= gameView.getWidth() - width  || x  <= 0) {
            xSpeed = -xSpeed;
        }
        x = x + speedfactor*2*xSpeed/12;
        if (y >= gameView.getHeight() - height - 300 || y  <= 80) {
            ySpeed = -ySpeed;
        }
        y = y + speedfactor*2*ySpeed/13;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }


        public void onDraw(Canvas canvas,int level) {
        int a = level;
        update(a);
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x  && x2 < x + width  && y2 > y  && y2 < y + height ;
    }
}