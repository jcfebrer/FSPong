package com.febrersoftware.pong.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.febrersoftware.pong.Global;
import com.febrersoftware.pong.GameView;
import com.febrersoftware.pong.SoundEffects;

public class Ball implements Sprite{

    int x = 0,y = 0;
    int ballXInc;
    int ballYInc;
    int ballWidth = 1;
    boolean ballVisible;
    int ballSpeed = 1;
    int edgeWidth = 1;
    GameView gameView;
    Resources res;

    public Ball(GameView gameView, Resources res) {

        this.gameView = gameView;
        this.res = res;

        ballXInc = ballSpeed;
        ballYInc = ballSpeed;
        ballVisible = false;

    }

    public void draw(Canvas canvas) {

        if(ballVisible) {
            Paint paint = new Paint();
            paint.setStrokeWidth(ballWidth * 2 * Global.scaleX);
            paint.setColor(Color.WHITE);

            canvas.drawPoint((x * Global.scaleX) + Global.posXCenter, (y * Global.scaleY) + Global.posYCenter, paint);
        }
    }

    @Override
    public Rect getCollisionShape() {
        float x1 = (x * Global.scaleX) + Global.posXCenter;
        float y1 = (y * Global.scaleY) + Global.posYCenter;
        return new Rect((int) x1, (int) y1, (int) (x1 + (ballWidth * 2 * Global.scaleX)), (int) (y1 + (ballWidth * 2 * Global.scaleX)));
    }

    public void update(){}


    public void update(Boundary boundary, Score score, Paddle paddle) {

        // Update the ball position
        x += ballXInc;
        y += ballYInc;

        // Check if a boundary has been hit
        if (Global.gameNumber == Global.GameType.FOOTBALL && ballVisible) // Football - left/right partial wall
        {
            if (y < boundary.soccerEdgeHeight + 2 || y > Global.pongHeight - 1 - boundary.soccerEdgeHeight - 1)
            {
                if (ballXInc < 0 && x <= edgeWidth && x >= 0)
                {
                    ballXInc = -ballXInc;
                    SoundEffects.Play(SoundEffects.s_PongBrick);
                }
                if (ballXInc > 0 && x >= Global.pongWidth - 1 - ballWidth - edgeWidth && x < Global.pongWidth - 1)
                {
                    ballXInc = -ballXInc;
                    SoundEffects.Play(SoundEffects.s_PongBrick);
                }
            }
        }

        if ((Global.gameNumber == Global.GameType.SQUASH || Global.gameNumber == Global.GameType.SOLO) && ballVisible) // Squash or Solo - left full wall
        {
            if (ballXInc < 0 && x <= edgeWidth)
            {
                ballXInc = -ballXInc;
                SoundEffects.Play(SoundEffects.s_PongBrick);
            }
        }

        // Check if the ball has exited the left or right side of the field, and update the score as required
        if (ballXInc > 0 && x >= Global.pongWidth - 1)
        {
            ballXInc = -ballXInc;
            if (!ballVisible)
            {
                ballVisible = true;
            }
            else
            {
                SoundEffects.Play(SoundEffects.s_PongGoal);
                paddle.touches = 0;

                if (!Global.attractMode)
                {
                    if (Global.gameNumber == Global.GameType.SQUASH)
                    {
                        if (paddle.squashActivePlayer == 1)
                        {
                            score.scoreRight++;
                        }
                        else
                        {
                            score.scoreLeft++;
                        }
                    }
                    else
                    {
                        score.scoreLeft++;
                    }
                    if (score.scoreLeft >= 15 || score.scoreRight >= 15)
                    {
                        Global.attractMode = true;
                    }
                }
                x = (int)((Global.pongWidth - 1) * 0.7);
                ballVisible = false;
            }
        }

        if (ballXInc < 0 && x <= -ballWidth)
        {
            ballXInc = -ballXInc;
            if (!ballVisible)
            {
                ballVisible = true;
            }
            else
            {
                SoundEffects.Play(SoundEffects.s_PongGoal);
                paddle.touches = 0;

                if (!Global.attractMode)
                {
                    score.scoreRight++;
                    if (score.scoreRight >= 15)
                    {
                        Global.attractMode = true;
                    }
                }
                x = (int)((Global.pongWidth - 1) * 0.3);
                ballVisible = false;
            }
        }

        // Bounce the ball off the top or bottom edge if needed
        if ((ballYInc < 0 && y < 2) || (ballYInc > 0 && y > Global.pongHeight - 1 - 4))
        {
            ballYInc = -ballYInc;
            SoundEffects.Play(SoundEffects.s_PongBrick);
        }
    }

    public void setSpeed()
    {
        if (Global.doubleSpeed)
        {
            // Switch to higher speed if not already set
            if (ballSpeed == 1)
            {
                ballXInc = ballXInc * 2;
                if (ballYInc == -1 || ballYInc == 1)
                {
                    ballYInc = ballYInc * 2; // from 1 line to 2 lines per step
                }
                else if (ballYInc < -1)
                {
                    ballYInc = -5; // from 3 lines to 5 lines per step
                }
                else if (ballYInc > 1)
                {
                    ballYInc = 5; // from 3 lines to 5 lines per step
                }

                ballSpeed = 2;
            }
        }
        else
        {
            // Switch to lower speed if not already set
            if (ballSpeed == 2)
            {
                ballXInc = ballXInc / 2;
                if (ballYInc == -2 || ballYInc == 2)
                {
                    ballYInc = ballYInc / 2; // from 2 lines down to 1 line per step
                }
                else if (ballYInc < -1)
                {
                    ballYInc = -3; // from 5 lines down to 3 lines per step
                }
                else if (ballYInc > 1)
                {
                    ballYInc = 3; // from 5 lines down to 3 lines per step
                }

                ballSpeed = 1;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth(){ return 0; }
    public int getHeight(){ return 0; }
}
