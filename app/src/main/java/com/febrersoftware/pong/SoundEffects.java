package com.febrersoftware.pong;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundEffects {

    public static int s_PongGoal;
    public static int s_PongPaddle;
    public static int s_PongBrick;

    private static SharedPreferences prefs;

    private static SoundPool soundPool;

    public static void Init(Activity activity)
    {
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

        s_PongGoal = soundPool.load(activity, R.raw.ponggoal, 0);
        s_PongPaddle = soundPool.load(activity, R.raw.pongpaddle, 0);
        s_PongBrick = soundPool.load(activity, R.raw.pongbrick, 0);
    }

    public static void Play(int sound)
    {
        if (!prefs.getBoolean("isSoundMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);
    }

    public static void Release()
    {
        soundPool.release();
        soundPool = null;
    }
}
