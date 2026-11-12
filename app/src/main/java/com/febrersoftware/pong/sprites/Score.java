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

    private final Bitmap[] redDigits = new Bitmap[10];
    private final Bitmap[] blueDigits = new Bitmap[10];
    private final Paint scorePaint = new Paint();

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
        if (number < 0 || number > 9) return;
        if (redDigits[0] == null) {
            initDigits();
        }
        Bitmap bmp = (color == Color.BLUE) ? blueDigits[number] : redDigits[number];
        if (bmp != null) {
            canvas.drawBitmap(bmp, (x * Global.scaleX) + Global.posXCenter, (y * Global.scaleY) + Global.posYCenter, scorePaint);
        }
    }

    private void initDigits() {
        int w1 = 8;
        int h1 = 15;
        int scaledW = (int) (w1 * Global.scaleX);
        int scaledH = (int) (h1 * Global.scaleY);
        if (scaledW <= 0) scaledW = 1;
        if (scaledH <= 0) scaledH = 1;

        for (int d = 0; d < 10; d++) {
            redDigits[d] = createDigitBitmap(d, Color.RED, w1, h1, scaledW, scaledH);
            blueDigits[d] = createDigitBitmap(d, Color.BLUE, w1, h1, scaledW, scaledH);
        }
    }

    private Bitmap createDigitBitmap(int number, int color, int w1, int h1, int scaledW, int scaledH) {
        int pos = number * 15;
        Bitmap bmp = Bitmap.createBitmap(w1, h1, Bitmap.Config.ARGB_8888);
        for (int i = pos; i < pos + 15 && i < sevenSegPAL.length; i++) {
            for (int j = 0; j < 8; j++) {
                if ((Integer.rotateLeft(sevenSegPAL[i], j) & 128) == 128) {
                    int x1 = (i - pos) % (w1 / 8) * 8 + j;
                    int y1 = (i - pos) / (w1 / 8);
                    if (x1 < w1 && y1 < h1) {
                        bmp.setPixel(x1, y1, color);
                    }
                }
            }
        }
        scorePaint.setFilterBitmap(false);
        return Bitmap.createScaledBitmap(bmp, scaledW, scaledH, false);
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
