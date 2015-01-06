package com.healthtapper.killthedevils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 5/1/15.
 */
public class GameView extends SurfaceView implements Runnable {

//    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<Sprite> spritesfemale = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    private Bitmap bmpBlood,fireball,fireballCover;
    private List<FireBallSprite> fireballsprites = new ArrayList<FireBallSprite>();
    Typeface font;
    private SoundPool sounds;
    private int sndmale;
    private int sndfemale;
    int score =  0;
    float pauseX,pauseY;
    float x,y,sX,sY,fX,fY,dx,dy,animx,animy;
    int gameover = 0;
    private Bitmap pause,tower,life;
    public static final String HIGHESTSCORE = "highestscore";
    boolean running =  false ;
    SurfaceHolder holder;
    Thread thread = null;
    static final long FPS = 20;
    public int level = 1;
    public int l1=1;
    public int l2=2;
    public int l3=3;
    public int l4=4;
    public int l5=5;
    public int leveldisplaylife = 40;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        fireball = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
        fireballCover = BitmapFactory.decodeResource(getResources(), R.drawable.fireballcover);
        pause = BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        tower = BitmapFactory.decodeResource(getResources(), R.drawable.tower);
        life = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        x = y = sX = sY = fX = fY = 0 ;
        font = Typeface.createFromAsset(context.getAssets(),"Toxia_FRE.ttf");
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sndmale = sounds.load(context,R.raw.screammale,1);
        sndfemale = sounds.load(context,R.raw.screammale,1);

        createMaleSprites();
        createFemaleSprites();
        createFireBallSprites();

    }


    private void createMaleSprites() {
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
        sprites.add(createSprite(R.drawable.bad1));
    }

    private void createFemaleSprites(){
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));
        spritesfemale.add(createSprite(R.drawable.good1));


    }

    private void createFireBallSprites(){
        fireballsprites.add(createFireBallSprite(R.drawable.fireball));
    }

    private Sprite createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    private FireBallSprite createFireBallSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new FireBallSprite(bmp);
    }

    public void render(Canvas canvas) throws InterruptedException {

        canvas.drawColor(Color.BLACK);

        Rect bottomRect = new Rect();
        Paint ourBlue = new Paint();
        //   ourBlue.setColor(Color.BLUE);
        ourBlue.setARGB(255,238,184,5);
        canvas.drawCircle(getWidth()/4,getHeight() ,60,ourBlue);
        canvas.drawCircle(3*getWidth()/4,getHeight(),60,ourBlue);
        ourBlue.setARGB(255,145,56,5);
        bottomRect.set(0, getHeight() - 45, getWidth(), getHeight());
        canvas.drawRect(bottomRect, ourBlue);
        Rect fortRect = new Rect();
        Paint ourFort = new Paint();
        ourFort.setARGB(255,238,184,5);
        fortRect.set(0, getHeight() - 10, getWidth(), getHeight());
        canvas.drawRect(fortRect, ourFort);

        Rect playRect = new Rect();
        Paint ourPlay = new Paint();
        //   ourBlue.setColor(Color.BLUE);
        ourPlay.setARGB(50,255,200,100);
        playRect.set(0, 70, getWidth(),getHeight() - 300 );
        canvas.drawRect(playRect, ourPlay);

        Rect topRect = new Rect();
        Paint ourGreen = new Paint();
        //ourGreen.setARGB(200,255,153,51);
        ourGreen.setARGB(255,238,184,5);
        topRect.set(0, 0, getWidth(), 70);
        Rect centerRect = new Rect();
        centerRect.set(0, getHeight() - 300, getWidth(), getHeight() - 290);
        canvas.drawRect(centerRect, ourGreen);

        Paint textpaint = new Paint();
        textpaint.setColor(Color.BLACK);
        textpaint.setTextAlign(Paint.Align.CENTER);
        textpaint.setTextSize(50);
        //    textpaint.setTypeface(font);

        Paint textpaint1 = new Paint();
        textpaint1.setTypeface(font);
        textpaint1.setARGB(100,255,255,255);
        textpaint1.setTextAlign(Paint.Align.CENTER);
        textpaint1.setTextSize(30);
        canvas.drawText("Tap on screen to launch fireball", getWidth() / 2, getHeight()-200, textpaint1);
        canvas.drawText("Kill devils ,avoid angels", getWidth() / 2, getHeight()-150, textpaint1);
        String leveltext1 = String.valueOf(level);
        textpaint1.setARGB(40,255,255,255);
        textpaint1.setTextSize(80);
        canvas.drawText(new StringBuilder().append("Level  ").append(leveltext1).toString(), getWidth() / 2, 300, textpaint1);


        for (int i = temps.size() - 1; i >= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas,level);
        }
        for (Sprite sprite : spritesfemale) {
            sprite.onDraw(canvas,level);
        }

        canvas.drawBitmap(tower, 0, getHeight() - 80, null);
        canvas.drawBitmap(tower, getWidth()  - tower.getWidth(), getHeight() - 80, null);


        canvas.drawRect(topRect, ourGreen);

        if(gameover <= 3) {
            canvas.drawBitmap(life, 5, 20, null);
        }
        if(gameover <= 2) {
            canvas.drawBitmap(life, 35, 20, null);
        }
        if(gameover <= 1) {
            canvas.drawBitmap(life, 65, 20, null);
        }
        if(gameover <= 0) {
            canvas.drawBitmap(life, 95, 20, null);
        }


        canvas.drawBitmap(fireballCover, getWidth() / 2 - fireballCover.getWidth() / 2, getHeight() - 100, null);

        String scoretext = String.valueOf(score);
        textpaint.setTextSize(48);
        canvas.drawText(new StringBuilder().append("Score : ").append(scoretext).toString(), getWidth() / 2, 50, textpaint);

        synchronized (getHolder()) {
            if(fX != 0 && fY != 0) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
//                    if (sprite.isCollision(getWidth() / 2 + animx, getHeight() - 60 + fireball.getHeight()/2 + animy)) {
                        //         if (sprite.isCollision(getWidth() / 2 + (7*animx)/8, getHeight() - 60 + fireball.getHeight() / 2 + (7*animy)/8)) {
                    if (sprite.isCollision(getWidth() / 2 + animx - fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy - fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx + fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy - fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx + fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy + fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx - fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy + fireball.getHeight()/4)
                            ) {


                    sounds.play(sndmale, 1.0f, 1.0f, 0, 0, 1.5f);
                        score += 10;
                        sprites.remove(sprite);
                        sprites.add(createSprite(R.drawable.bad1));
                        temps.add(new TempSprite(temps, this, getWidth() / 2 - fireball.getWidth() / 2 + animx, getHeight() - 30 + animy, bmpBlood));
                        //       fireballDestroy = true;
                        for (int a = fireballsprites.size() - 1; a >= 0; a--) {
                            FireBallSprite fire = fireballsprites.get(a);
                            fireballsprites.remove(fire);
                            fY = fX = 0;
                            x = y = 0;
                            fireballsprites.add(createFireBallSprite(R.drawable.fireball));
                        }
                        break;
                    }
                }
            }

            canvas.drawBitmap(pause,getWidth()-pause.getWidth() -5, -2, null);

            if(fX != 0 && fY != 0) {
                for (int i = spritesfemale.size() - 1; i >= 0; i--) {
                    Sprite sprite = spritesfemale.get(i);

                    if (sprite.isCollision(getWidth() / 2 + animx - fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy - fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx + fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy - fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx + fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy + fireball.getHeight()/4)
                            || sprite.isCollision(getWidth() / 2 + animx - fireball.getWidth()/4, getHeight() - 60 + fireball.getHeight()/2 + animy + fireball.getHeight()/4)
                            ) {
                        sounds.play(sndfemale, 1.0f, 1.0f, 0, 0, 1.5f);
                        score -= 5;
                        spritesfemale.remove(sprite);
                        spritesfemale.add(createSprite(R.drawable.good1));
                        temps.add(new TempSprite(temps, this, getWidth() / 2 - fireball.getWidth() / 2 + animx, getHeight() - 30 + animy, bmpBlood));
                        gameover++;
                        for (int a = fireballsprites.size() - 1; a >= 0; a--) {
                            FireBallSprite fire = fireballsprites.get(a);
                            fireballsprites.remove(fire);
                            fY = fX = 0;
                            x = y = 0;
                            fireballsprites.add(createFireBallSprite(R.drawable.fireball));
                        }
                        break;
                    }

                }
            }
        }
        if (gameover == 4) {

            int highestscore = Splash.pref.getInt(HIGHESTSCORE, 0);
            if(highestscore < score){
                SharedPreferences.Editor editor = Splash.pref.edit();
                editor.putInt(HIGHESTSCORE, score);
                editor.commit();
            }
            Rect hideRect = new Rect();
            ourGreen.setARGB(255,238,184,5);
            hideRect.set(getWidth()/2 - 120, 0, getWidth()/2 + 120, 70);
            Rect hidelifeRect = new Rect();
            hidelifeRect.set(0, 0,50, 70);
            canvas.drawRect(hideRect, ourGreen);
            canvas.drawRect(hidelifeRect, ourGreen);
            scoretext = String.valueOf(score);
            canvas.drawText(new StringBuilder().append("Score : ").append(scoretext).toString(), getWidth() / 2, 50, textpaint);
            gameoverActivity();
            score = 0;
            gameover = 0;
            level = 1;
            l1 = 1;
            l2 = 2;
            l3 = 3;
            l4 = 4;
            l5 = 5;
        }

        if (fX != 0 && fY != 0) {

            //         canvas.drawBitmap(fireball, getWidth() / 2 - fireball.getWidth() / 2 + animx, getHeight() - 60 + animy, null);
            for (FireBallSprite sprite : fireballsprites) {
                sprite.onDraw(canvas,getWidth()/2 - fireball.getWidth()/2 + animx,getHeight() - 60 + animy);
            }
            // }
            animx = animx + dx /9;
            animy = animy + dy /9;
        }


        if(score >= 50 && l1 == 1){
            leveldisplaylife --;
            textpaint.setColor(Color.RED);
            textpaint.setTypeface(font);
            textpaint.setTextSize(70);
            if(leveldisplaylife >=0) {
                canvas.drawText("Level  2", getWidth() / 2, getHeight() / 2, textpaint);
            } else{
                leveldisplaylife = 40;
                l1 ++;
                level ++;
            }
        }

//        if(l2 == 4){
//            l2++;
//            thread.sleep(200);
//        }
//
//        if(l2 == 3){
//            l2 ++;
//            String leveltext = String.valueOf(level);
//            textpaint.setColor(Color.RED);
//            textpaint.setTextSize(50);
//            canvas.drawText(new StringBuilder().append("Level  ").append(leveltext).toString(), getWidth() / 2, getHeight()/2, textpaint);
//        }
//
//        if(score >= 100 && l2 == 2){
//            l2 ++;
//            level++;
//            String leveltext = String.valueOf(level);
//            textpaint.setColor(Color.RED);
//            textpaint.setTextSize(50);
//            canvas.drawText(new StringBuilder().append("Level  ").append(leveltext).toString(), getWidth() / 2, getHeight()/2, textpaint);
//        }

        if(score >= 150 && l2 == 2){
            leveldisplaylife --;
            textpaint.setColor(Color.RED);
            textpaint.setTypeface(font);
            textpaint.setTextSize(70);
            if(leveldisplaylife >=0) {
                canvas.drawText("Level  3", getWidth() / 2, getHeight() / 2, textpaint);
            } else{
                leveldisplaylife = 40;
                l2 ++;
                level ++;
            }
        }

        if(score >= 250 && l3 == 3){
            leveldisplaylife --;
            textpaint.setColor(Color.RED);
            textpaint.setTextSize(70);
            textpaint.setTypeface(font);
            if(leveldisplaylife >=0) {
                canvas.drawText("Level  4", getWidth() / 2, getHeight() / 2, textpaint);
            } else{
                leveldisplaylife = 40;
                l3 ++;
                level ++;
            }
        }

        if(score >= 300 && l4 == 4){
            leveldisplaylife --;
            textpaint.setColor(Color.RED);
            textpaint.setTextSize(70);
            textpaint.setTypeface(font);
            if(leveldisplaylife >=0) {
                canvas.drawText("Level  4", getWidth() / 2, getHeight() / 2, textpaint);
            } else{
                leveldisplaylife = 40;
                l4 ++;
                level ++;
            }
        }
    }


    public void pause(){
        running = false;
        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    public void resume(){
        running = true;
        thread = new Thread(this);
        thread.start();

    }


    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while(running){
            if(!holder.getSurface().isValid())
                continue;

                Canvas c = null;
                startTime = System.currentTimeMillis();
                try {
                    c = holder.lockCanvas();
                    synchronized (holder) {
                        try {
                            render(c);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0)
                        thread.sleep(sleepTime);
                    else
                        thread.sleep(10);
                } catch (Exception e) {
                }

        }

    }


    public void gameoverActivity(){
        Context context = getContext();
        Intent intent = new Intent("com.healthtapper.GAMEOVER");
        Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
        bundle.putInt("SCORE", score);
//Add the bundle to the intent
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void pauseActivity(){
        Context context = getContext();
        Intent intent = new Intent("com.healthtapper.PAUSEACTIVITY");
        context.startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 200) {

            lastClick = System.currentTimeMillis();

            if ((getWidth() / 2 - fireball.getWidth() / 2 + animx) <= 0 || (getWidth() / 2 - fireball.getWidth() / 2 + animx) >= getWidth()
                    || (getHeight() - 30 + animy) <= 0 || (getHeight() - 30 + animy) >= getHeight() || (x == 0 && y == 0)) {
                x = event.getX();
                y = event.getY();

                if(y < getHeight() - 80){
                    sX = getWidth() / 2 - fireball.getWidth() / 2;
                    sY = getHeight() - 60;
                    dx = dy = animx = animy = 0;
                    fX = x;
                    fY = y;
                    dx = fX - sX;
                    dy = fY - sY;
                }else{
                    x = y = 0;
                }

            }
            //   }
            pauseX = event.getX();
            pauseY = event.getY();

            if (pauseX >= getWidth() - pause.getWidth() - 5 && pauseX <= getWidth() && pauseY >= 0 && pauseY <= pause.getHeight()) {
                //               setPausedView();
         //       gameLoopThread.setPaused(true);
                fX = fY = x = y = 0;

                 pauseActivity();
            }

        }

        return true;
    }

}
