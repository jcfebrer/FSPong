package com.febrersoftware.pong.sprites;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public interface Sprite {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();

    public void update();
    public void draw(Canvas canvas);
    public Rect getCollisionShape ();
}
