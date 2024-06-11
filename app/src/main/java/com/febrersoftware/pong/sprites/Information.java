package com.febrersoftware.pong.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.febrersoftware.pong.GameThread;
import com.febrersoftware.pong.Global;

public class Information {
    public void draw(Canvas canvas, GameThread thread)
    {
        if(!Global.debug)
            return;

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(3 * Global.scaleX);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.format("UPS: %.2f", thread.getAverageUPS()) + String.format("FPS: %.2f", thread.getAverageFPS()), (Global.screenWidth / 2), Global.screenHeight - 50, paint);
    }
}
