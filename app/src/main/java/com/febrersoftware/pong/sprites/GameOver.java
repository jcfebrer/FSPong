package com.febrersoftware.pong.sprites;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.febrersoftware.pong.GameView;
import com.febrersoftware.pong.Global;
import com.febrersoftware.pong.R;

public class GameOver {
    public void draw(Canvas canvas, GameView gameView, Activity activity)
    {
        //Final de la partida
        if (Global.attractMode) {
            Paint paint = new Paint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(10 * Global.scaleX);
            paint.setColor(Color.WHITE);
            canvas.drawText(gameView.getContext().getString(R.string.gameover), (Global.screenWidth / 2), (Global.screenHeight / 2), paint);

            gameView.getHolder().unlockCanvasAndPost(canvas);
            waitBeforeExiting(activity);
        }
    }

    private void waitBeforeExiting(Activity activity) {

        try {

            Thread.sleep(3500);
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep (long timeToWait) {
        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
