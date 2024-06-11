package com.febrersoftware.pong.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.febrersoftware.pong.Global;
import com.febrersoftware.pong.GameView;

public class Score implements Sprite{

    GameView gameView;
    Resources res;

    int scoreLeft;
    int scoreRight;
    int leftDig1X = 21;
    int leftDig2X = 29;
    int rightDig1X = 43;
    int rightDig2X = 51;

    // Font bitmap for the 7 segment on-screen scoring display
    public byte[] sevenSegPAL = new byte[] {
            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC,
            (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 0 */

            (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C,
            (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, /* 1 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0xC0, (byte) 0xC0, (byte) 0xC0, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 2 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 3 */

            (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, /* 4 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0xC0, (byte) 0xC0, (byte) 0xC0, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 5 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0xC0, (byte) 0xC0, (byte) 0xC0, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 6 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C,
            (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, /* 7 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 8 */

            (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC, (byte) 0xFC, (byte) 0xFC,
            (byte) 0xFC, (byte) 0x0C, (byte) 0x0C, (byte) 0x0C, (byte) 0xFC, (byte) 0xFC, (byte) 0xFC, /* 9 */
    };

    public Score(GameView gameView, Resources res) {

        this.gameView = gameView;
        this.res = res;

        scoreLeft = 0;
        scoreRight = 0;
    }

    public void draw(Canvas canvas) {

        // Only draw first digit if score is >9
        if (scoreLeft > 9)
        {
            drawDigit(leftDig1X, 3, 1, canvas, Color.RED);
            drawDigit(leftDig2X, 3, scoreLeft - 10, canvas, Color.RED);
        }
        else
        {
            drawDigit(leftDig2X, 3, scoreLeft, canvas, Color.RED);
        }

        if (Global.gameNumber != Global.GameType.SOLO) // not solo
        {
            // Only draw first digit if score is >9
            if (scoreRight > 9)
            {
                drawDigit(rightDig1X, 3, 1, canvas, Color.BLUE);
                drawDigit(rightDig2X, 3, scoreRight - 10, canvas, Color.BLUE);
            }
            else
            {
                drawDigit(rightDig2X, 3, scoreRight, canvas, Color.BLUE);
            }
        }
    }

    void drawDigit(int x, int y, int number, Canvas canvas, int color)
    {
        drawBinaryBitmap(x, y, sevenSegPAL, number * 15, 8, 15, color, canvas);
    }

    public static void drawBinaryBitmap(int x, int y, byte[] pixels, int pos, int width,
                                        int height, int color, Canvas canvas)
    {
        Paint paint = new Paint();
        //paint.setStrokeWidth(Global.strokeWidth * Global.scaleX);
        paint.setColor(Color.RED);

        int x1, y1;
        int w1, h1;

        w1 = width; //cada byte son 8 bits
        h1 = height;

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w1, h1, config);

        //int line = 0;

        for (int i = pos; i < pixels.length; i++)
            for (int j = 0; j < 8; j++) //recorremos cada bit del byte y si ....
                if ((Integer.rotateLeft(pixels[i], j) & 128) == 128) //si el valor es un 1, pintamos
                {
                    x1 = (i - pos) % (w1 / 8) * 8 + j; //((i % (w1 / 8)) * 8) + (7 - j);
                    y1 = (i - pos) / (w1 / 8); //line;

                    if (x1 < w1 && y1 < h1)
                        bmp.setPixel(x1, y1, color);
                }

        paint.setFilterBitmap(false);
        Bitmap bmpScale = Bitmap.createScaledBitmap(bmp, (int) (w1 * Global.scaleX), (int) (h1 * Global.scaleY), false);
        canvas.drawBitmap(bmpScale, (x * Global.scaleX) + Global.posXCenter, (y * Global.scaleY) + Global.posYCenter, paint);
    }

    @Override
    public Rect getCollisionShape() {
        return null;
    }

    public void update() {
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
