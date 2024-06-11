package com.febrersoftware.pong.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.febrersoftware.pong.Global;
import com.febrersoftware.pong.GameView;

public class Boundary implements Sprite{

    int soccerEdgeHeight = 25;

    GameView gameView;
    Resources res;

    public Boundary(GameView gameView, Resources res) {
        this.gameView = gameView;
        this.res = res;
    }

    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStrokeWidth(Global.scaleX);
        paint.setColor(Color.WHITE);

        //canvas.drawRect(0, 0, Global.pongWidth * Global.scaleX, Global.pongHeight * Global.scaleY, paint);

        //linea de puntos superior e inferior
        for (int i2 = 0; i2 < Global.pongWidth - 1; i2 = i2 + 3)
        {
            canvas.drawPoint((i2 * Global.scaleX) + Global.posXCenter, 0 + Global.posYCenter, paint);
            canvas.drawPoint((i2 * Global.scaleX) + Global.posXCenter, ((Global.pongHeight - 2) * Global.scaleY) + Global.posYCenter, paint);
        }

        // Goals for Soccer
        if (Global.gameNumber == Global.GameType.FOOTBALL)
        {
            canvas.drawLine(0 + Global.posXCenter, 0 + Global.posYCenter, 0 + Global.posXCenter, (soccerEdgeHeight * Global.scaleY) + Global.posYCenter, paint);
            canvas.drawLine(((Global.pongWidth - 1) * Global.scaleX) + Global.posXCenter, 0 + Global.posYCenter, ((Global.pongWidth - 1) * Global.scaleX) + Global.posXCenter, (soccerEdgeHeight * Global.scaleY) + Global.posYCenter, paint);

            canvas.drawLine(0 + Global.posXCenter, ((Global.pongHeight - 1) * Global.scaleY) + Global.posYCenter, 0 + Global.posXCenter, ((Global.pongHeight - 1 - soccerEdgeHeight) * Global.scaleY) + Global.posYCenter, paint);
            canvas.drawLine(((Global.pongWidth - 1) * Global.scaleX) + Global.posXCenter, ((Global.pongHeight - 1) * Global.scaleY) + Global.posYCenter, ((Global.pongWidth - 1) * Global.scaleX) + Global.posXCenter, ((Global.pongHeight - 1 - soccerEdgeHeight) * Global.scaleY) + Global.posYCenter, paint);
        }

        //Left wall for Squash or Solo
        if (Global.gameNumber == Global.GameType.SQUASH || Global.gameNumber == Global.GameType.SOLO)
        {
            canvas.drawLine(0 + Global.posXCenter, 0 + Global.posYCenter, 0 + Global.posXCenter, ((Global.pongHeight - 1) * Global.scaleY) + Global.posYCenter, paint);
        }

        //Centre line for Tennis or Soccer
        int centreX = Global.pongWidth / 2;
        if (Global.gameNumber == Global.GameType.TENNIS || Global.gameNumber == Global.GameType.FOOTBALL)
        {
            for (int i = 4; i < Global.pongHeight - 2; i = i + 4)
            {
                canvas.drawPoint((centreX * Global.scaleX) + Global.posXCenter, (i * Global.scaleY) + Global.posYCenter, paint);
            }
        }
    }

    @Override
    public Rect getCollisionShape() {
        return null;
    }

    public void update() {
    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getWidth(){ return 0; }
    public int getHeight(){ return 0; }
}
