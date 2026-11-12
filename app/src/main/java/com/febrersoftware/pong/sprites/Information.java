package com.febrersoftware.pong.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.febrersoftware.pong.GameThread;
import com.febrersoftware.pong.Global;

public class Information {
    private final Paint paint = new Paint();

    public void draw(Canvas canvas, GameThread thread)
    {
        if(!Global.debug)
            return;

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(2.2f * Global.scaleX);
        paint.setColor(Color.BLUE);

        boolean hwAccelerated = canvas.isHardwareAccelerated();
        String line1 = String.format("UPS: %.1f | FPS: %.1f", thread.getAverageUPS(), thread.getAverageFPS());
        String line2 = String.format("HW Accel: %s | Mode: %s", (hwAccelerated ? "YES" : "NO"), Global.gameNumber.name());
        String line3 = String.format("Screen: %dx%d | Level: %d", Global.screenWidth, Global.screenHeight, Global.level);
        String line4 = String.format("Scale: %.2f x %.2f", Global.scaleX, Global.scaleY);

        float x = 20;
        float y = 40;
        float spacing = 35;

        canvas.drawText(line1, x, y, paint);
        canvas.drawText(line2, x, y + spacing, paint);
        canvas.drawText(line3, x, y + (spacing * 2), paint);
        canvas.drawText(line4, x, y + (spacing * 3), paint);
    }
}
