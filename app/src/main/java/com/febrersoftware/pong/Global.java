package com.febrersoftware.pong;

import android.graphics.Color;

public class Global {
    public enum GameType {
        TENNIS,
        FOOTBALL,
        SQUASH,
        SOLO
    }
    public static int screenWidth;
    public static int screenHeight;
    public static float scaleX;
    public static float scaleY;
    public static float posXCenter;
    public static float posYCenter;
    public static int pongWidth = 80; //tamaño original del PONG
    public static int pongHeight = 116; //tamaño original del PONG
    public static GameType gameNumber = GameType.TENNIS; //1=Tennis, 2=Football, 3=Squash, 4=Solo
    public static boolean bigAngles = false;
    public static boolean doubleSpeed = false;
    public static int paddleHeight = 14;  // tamaño pequeño = 7
    public static int paddleWidth = 2;
    public static boolean attractMode = false; //modo sin puntuación
    public static boolean player2ComputerMode = true;
    public static int level = 1;
    public static int color = Color.GREEN;
    public static boolean debug = false;
}
