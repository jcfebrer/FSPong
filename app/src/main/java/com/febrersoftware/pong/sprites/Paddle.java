package com.febrersoftware.pong.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.febrersoftware.pong.Functions;
import com.febrersoftware.pong.Global;
import com.febrersoftware.pong.GameView;
import com.febrersoftware.pong.SoundEffects;

import java.util.Random;

public class Paddle implements Sprite{

    //posiciones iniciales de la palas
    public int paddleLeftY = Global.screenHeight / 2;
    public int paddleRightY = Global.screenHeight / 2;

    //indicador del jugador activo en el squash
    int squashActivePlayer = 1;
    int paddleMove = 12;
    int randomPos = 0;
    int touches = 0; //toques con la pala

    GameView gameView;
    Resources res;

    public Paddle(GameView gameView, Resources res) {

        this.gameView = gameView;
        this.res = res;
    }

    public void draw(Canvas canvas) {
    }

    public void draw(Canvas canvas, Ball ball) {

        int leftPaddleY = (int) Functions.Map(paddleLeftY, 0, Global.screenHeight - 1, 2 - Global.paddleHeight, Global.pongHeight - 2);
        int rightPaddleY = (int)Functions.Map(paddleRightY, 0, Global.screenHeight - 1, 2 - Global.paddleHeight, Global.pongHeight - 2);

        if (Global.gameNumber == Global.GameType.TENNIS || Global.gameNumber == Global.GameType.FOOTBALL) // Tennis or Football
        {
            drawPaddle(canvas, 1, leftPaddleY, ball);
            drawPaddle(canvas, 5, rightPaddleY, ball);
        }
        if (Global.gameNumber == Global.GameType.FOOTBALL) // Football
        {
            drawPaddle(canvas, 4, leftPaddleY, ball);
            drawPaddle(canvas, 2, rightPaddleY, ball);
        }
        if (Global.gameNumber == Global.GameType.SQUASH) // Squash
        {
            drawPaddle(canvas, 4, leftPaddleY, ball);
            drawPaddle(canvas, 3, rightPaddleY, ball);
        }
        if (Global.gameNumber == Global.GameType.SOLO) // Solo
        {
            drawPaddle(canvas,  3, rightPaddleY, ball);
        }
    }

    void drawPaddle(Canvas canvas, int paddleNum, int paddleY, Ball ball)
    {
        Paint paint = new Paint();
        paint.setStrokeWidth(Global.paddleWidth * Global.scaleX);

        int bat1X = 2;
        int bat2X = 18;
        int bat3X = 58;
        int bat4X = 60;
        int bat5X = 76;

        int paddleX = 0;
        int c = 0;
        if (paddleNum == 1) // Left player left paddle
        {
            c = Color.RED;
            paddleX = bat1X;
        }
        if (paddleNum == 5) // Right player right paddle
        {
            c = Color.BLUE;
            paddleX = bat5X;
        }
        if (paddleNum == 4) // Left player mid/squash paddle
        {
            c = Color.RED;
            paddleX = bat4X;
        }
        if (paddleNum == 2) // Right player mid paddle
        {
            c = Color.BLUE;
            paddleX = bat2X;
        }
        if (paddleNum == 3) // Right player squash paddle
        {
            c = Color.BLUE;
            paddleX = bat3X;
        }
        int y1 = paddleY;
        int y2 = paddleY + Global.paddleHeight;
        if (y1 < 1)
        {
            y1 = 1;
        }
        if (y2 > Global.pongHeight - 2)
        {
            y2 = Global.pongHeight - 2;
        }

        paint.setColor(c);
        canvas.drawLine((paddleX * Global.scaleX) + Global.posXCenter, (y1 * Global.scaleY) + Global.posYCenter, (paddleX * Global.scaleX) + Global.posXCenter, (y2 * Global.scaleY) + Global.posYCenter, paint);

        //comprobamos si la pelota colisiona con un paddle
        if (ball.ballVisible && !Global.attractMode) {
            checkPaddleCollision(paddleNum, paddleX, paddleY, ball);
        }
    }

    private void computerPlay(Ball ball) {
        boolean applyRandom;

        int newPos = ball.y - (Global.paddleHeight / 2);
        int maxTouches = 0;// sin fallar al menos hasta que se hagan X toques.

        switch(Global.level)
        {
            case 1:
                maxTouches = 8;
                break;
            case 2:
                maxTouches = 16;
                break;
            case 3:
                maxTouches = 30;
                break;
        }

        //aplicamos la posición aleatoria si randomPos == 0 y se ha tocado con la pala.
        if(randomPos == 0 && touches > maxTouches) {
            Random random = new Random();

            int levelToApply = Global.level;
            switch(Global.gameNumber)
            {
                case TENNIS: //tennis
                    levelToApply = Global.level;
                    break;
                case FOOTBALL:
                    // si el juego es hockey y el balón esta más la izquierda, reducir la dificultad
                    if(Global.level > 1 && ball.x < (Global.pongWidth / 2))
                        levelToApply = Global.level - 1;
                    break;
                case SQUASH: //squash
                    levelToApply = Global.level;
                    break;
            }

            randomPos = random.nextInt((Global.paddleHeight) / levelToApply) * (random.nextBoolean() ? -1 : 1);
        }

        //si la bola va hacia el lado derecho, aplicar la nueva posición
        if (ball.ballXInc > 0) {
            applyRandom = true;
        }
        else {
            applyRandom = false;
            randomPos = 0; // forzamos el cálculo random de la nueva posición
        }

        //si estamos jugando al squash y le toca al jugador uno, no aplicamos la nueva posición
        if (Global.gameNumber == Global.GameType.SQUASH && squashActivePlayer == 1)
            applyRandom = false;


        if(ball.ballVisible) {
            if (applyRandom) {
                newPos += randomPos;
                paddleRightY = (int) Functions.Map(newPos, 2 - Global.paddleHeight, Global.pongHeight - 2, 0, Global.screenHeight - 1);
            } else {
                if (Global.gameNumber != Global.GameType.SQUASH) {
                    //mantenemos el paddle siguiendo la posición Y de la bola
                    paddleRightY = (int) Functions.Map(newPos, 2 - Global.paddleHeight, Global.pongHeight - 2, 0, Global.screenHeight - 1);
                }
            }
        }
    }

    private void checkPaddleCollision(int paddleNum, int paddleX, int paddleY, Ball ball) {
        // Intercept ball
        if ((ball.x >= paddleX - 1) && (ball.x <= paddleX + Global.paddleWidth)) {
            if ((ball.y >= paddleY - 1) && (ball.y <= paddleY + Global.paddleHeight)) {

                SoundEffects.Play(SoundEffects.s_PongPaddle);
                touches++;

                if (Global.gameNumber == Global.GameType.SQUASH) //Squash
                {
                    if (ball.ballXInc < 0 || (squashActivePlayer == 1 && paddleNum != 4) || (squashActivePlayer == 2 && paddleNum != 3)) {
                        // Either the bat is not active or it is passing through the left bat
                        // so don't intercept on this game
                        return;
                    }
                    if (ball.ballXInc > 0 && (paddleNum == 4 && squashActivePlayer == 1)) {
                        ball.ballXInc = -ball.ballXInc;
                        squashActivePlayer = 2;
                    }
                    if (ball.ballXInc > 0 && (paddleNum == 3 && squashActivePlayer == 2)) {
                        ball.ballXInc = -ball.ballXInc;
                        squashActivePlayer = 1;
                    }
                } else if (Global.gameNumber == Global.GameType.SOLO) //Solo
                {
                    if (ball.ballXInc > 0) {
                        ball.ballXInc = -ball.ballXInc;
                        squashActivePlayer = 2;
                    }
                } else {
                    if (ball.ballXInc < 0 && (paddleNum == 1 || paddleNum == 4)) {
                        ball.ballXInc = -ball.ballXInc;
                    }
                    if (ball.ballXInc > 0 && (paddleNum == 2 || paddleNum == 3 || paddleNum == 5)) {
                        ball.ballXInc = -ball.ballXInc;
                    }
                }
                if (Global.bigAngles && ball.y < paddleY + Global.paddleHeight / 4) {
                    if (ball.ballSpeed == 1) {
                        ball.ballYInc = -3;
                    } else {
                        ball.ballYInc = -5;
                    }
                } else if (ball.y < paddleY + Global.paddleHeight / 2) {
                    ball.ballYInc = -ball.ballSpeed;
                } else if (!Global.bigAngles || ball.y < paddleY + Global.paddleHeight * 3 / 4) {
                    ball.ballYInc = ball.ballSpeed;
                } else {
                    if (ball.ballSpeed == 1) {
                        ball.ballYInc = 3;
                    } else {
                        ball.ballYInc = 5;
                    }
                }
            }
        }
    }

    public void UpLeft()
    {
        if(paddleLeftY > 0)
            paddleLeftY -= paddleMove;
    }

    public void DownLeft()
    {
        if(paddleLeftY < Global.screenHeight)
            paddleLeftY += paddleMove;
    }

    public void UpRight()
    {
        if(paddleRightY > 0)
            paddleRightY -= paddleMove;
    }

    public void DownRight()
    {
        if(paddleRightY < Global.screenHeight)
            paddleRightY += paddleMove;
    }

    @Override
    public Rect getCollisionShape() {
        return null;
    }

    public void update() {
    }

    public void update(Ball ball) {

        //si estamos en modo de 1 solo jugador
        if(Global.player2ComputerMode)
        {
            computerPlay(ball);
        }
    }

    //getters
    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getWidth(){ return 0; }
    public int getHeight(){ return 0; }
}
