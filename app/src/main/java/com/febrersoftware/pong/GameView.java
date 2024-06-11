package com.febrersoftware.pong;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.febrersoftware.pong.sprites.Ball;
import com.febrersoftware.pong.sprites.Boundary;
import com.febrersoftware.pong.sprites.GameOver;
import com.febrersoftware.pong.sprites.Information;
import com.febrersoftware.pong.sprites.Paddle;
import com.febrersoftware.pong.sprites.Score;

// https://github.com/heyletscode/2D-Game-In-Android-Studio/tree/master/IHaveToFly
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private SharedPreferences prefs;
    private Boundary boundary;
    private Ball ball;
    private Paddle paddle;
    private Score score;
    private Information information;
    private GameOver gameover;
    private GameActivity activity;

    private SparseArray<PointF> mActivePointers;

    public GameView(GameActivity activity, int screenWidth, int screenHeight) {
        super(activity);

        this.activity = activity;

        Global.screenWidth = screenWidth;
        Global.screenHeight = screenHeight;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        //Inicializamos PONG
        Global.attractMode = false;

        boundary = new Boundary(this, getResources());
        score = new Score(this, getResources());
        ball = new Ball(this, getResources());
        paddle = new Paddle(this, getResources());

        mActivePointers = new SparseArray<>();

        gameover = new GameOver();
        information = new Information();

        InitView(this);
    }

    private void InitView(GameView view)
    {
        Global.scaleX = (Global.screenHeight / Global.pongHeight) + 5;
        Global.scaleY = (Global.screenHeight / Global.pongHeight);

        float pongWidthRat = Global.pongWidth * Global.scaleX;
        float pongHeightRat = Global.pongHeight * Global.scaleY;

        Global.posXCenter = (Global.screenWidth - pongWidthRat) / 2;
        Global.posYCenter = (Global.screenHeight - pongHeightRat) / 2;

        //canvas.translate(posXCenter, posYCenter);
        //canvas.scale(Global.scaleX, Global.scaleY);

        //desactivamos la aceleración por software
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        /*view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, ratX),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, ratY),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0)
                );
        scale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setLayerType(View.LAYER_TYPE_NONE, null);
            }
        });
        scale.start();*/

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(view);

        thread = new GameThread(view, surfaceHolder);

        //view.setTranslationX(Global.posXCenter);
        //view.setTranslationY(Global.posYCenter);
        //view.setScaleX(Global.scaleX);
        //view.setScaleY(Global.scaleY);
        //view.setPivotX(0);
        //view.setPivotY(0);
    }

    public void update () {

        //background1.update();
        //background2.update();

        boundary.update();
        score.update();
        ball.setSpeed();
        paddle.update(ball);
        ball.update(boundary, score, paddle);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Global.color);

        //background1.draw(canvas, paint);
        //background2.draw(canvas, paint);

        boundary.draw(canvas);
        score.draw(canvas);
        paddle.draw(canvas, ball);
        ball.draw(canvas);

        information.draw(canvas, thread);
        gameover.draw(canvas, this, activity);
    }

    public void pause () {
        thread.stopLoop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers

                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = mActivePointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }

                    if (point.x > (int)(Global.screenWidth / 2)){
                        if(!Global.player2ComputerMode)
                            paddle.paddleRightY = (int)point.y;
                        else
                            paddle.paddleLeftY = (int)point.y;
                    }
                    else {
                        paddle.paddleLeftY = (int) point.y;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                mActivePointers.remove(pointerId);
                break;
            }
        }

        invalidate();
        return true;
    }


    public void Down()
    {
        paddle.DownLeft();
    }

    public void Up()
    {
        paddle.UpLeft();
    }

    public void Fire()
    {
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (thread.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            thread = new GameThread(this, surfaceHolder);
        }
        thread.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
