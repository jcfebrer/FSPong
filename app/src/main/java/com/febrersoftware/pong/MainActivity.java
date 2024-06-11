package com.febrersoftware.pong;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    private boolean isSoundMute;
    TextView gameTennis, gameHockey, gameSquash, gameTutorial;
    SwitchCompat switchSpeed, switchAngle, switchSize, switch2Player, switchBlack;
    ImageView imageViewPlayer;
    RadioGroup radioNivelGrupo;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        SoundEffects.Init(activity);

        setContentView(R.layout.activity_main);


        //Publicidad
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });*/

        //LinearLayout layout_main = findViewById(R.id.layout_publicity);
        //AdView adView = new AdView(this);

        //adView.setAdSize(AdSize.BANNER);

        //TEST: ca-app-pub-3940256099942544/6300978111
        //adView.setAdUnitId("ca-app-pub-4278289028669575/1862615195");

        //layout_main.addView(adView);


        /*AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        //FIN PUBLICIDAD


        gameTennis = findViewById(R.id.playTennis);
        gameHockey = findViewById(R.id.playHockey);
        gameSquash = findViewById(R.id.playSquash);
        gameTutorial = findViewById(R.id.tutorial);

        imageViewPlayer = findViewById(R.id.imageViewPlayer);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fonts/PressStart2PRegular.ttf");
        gameTennis.setTypeface(typeface);
        gameHockey.setTypeface(typeface);
        gameSquash.setTypeface(typeface);
        gameTutorial.setTypeface(typeface);

        switchSpeed = findViewById(R.id.switchSpeed);
        switchAngle = findViewById(R.id.switchAngle);
        switchSize = findViewById(R.id.switchSize);
        switch2Player = findViewById(R.id.switchPlayer);
        switchBlack = findViewById(R.id.switchBlack);

        radioNivelGrupo = findViewById(R.id.radioNivelGrupo);

        imageViewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2Player.setChecked(!switch2Player.isChecked());
            }
        });

        switchSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global.doubleSpeed = isChecked;
            }
        });

        switchAngle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global.bigAngles = isChecked;
            }
        });

        switchSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Global.paddleHeight = 7;
                else
                    Global.paddleHeight = 14;
            }
        });

        switch2Player.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Global.player2ComputerMode = !isChecked;
            }
        });

        switchBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Global.color = Color.BLACK;
                else
                    Global.color = Color.GREEN;
            }
        });


        radioNivelGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioNivel1){
                    Global.level = 1;
                }else if (checkedId == R.id.radioNivel2){
                    Global.level = 2;
                }else if (checkedId == R.id.radioNivel3){
                    Global.level = 3;
                }
            }
        });


        gameTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.gameNumber = Global.GameType.TENNIS;
                SoundEffects.Play(SoundEffects.s_PongGoal);
                startActivity(new Intent(activity, GameActivity.class));
            }
        });

        gameHockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.gameNumber = Global.GameType.FOOTBALL;
                SoundEffects.Play(SoundEffects.s_PongGoal);
                startActivity(new Intent(activity, GameActivity.class));
            }
        });

        gameSquash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.gameNumber = Global.GameType.SQUASH;
                SoundEffects.Play(SoundEffects.s_PongGoal);
                startActivity(new Intent(activity, GameActivity.class));
            }
        });

        gameTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundEffects.Play(SoundEffects.s_PongGoal);
                startActivity(new Intent(activity, Tutorial.class));
            }
        });

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        //Sound : icon toggle
        isSoundMute = prefs.getBoolean("isSoundMute", false);

        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isSoundMute) {
            volumeCtrl.setImageResource(R.drawable.ic_volume_off);
        } else {
            volumeCtrl.setImageResource(R.drawable.ic_volume_up);
        }

        //dejamos los swiches con la posición correcta
        switchSpeed.setChecked(Global.doubleSpeed);
        switchAngle.setChecked(Global.bigAngles);
        switch2Player.setChecked(!Global.player2ComputerMode);
        if(Global.paddleHeight == 14)
            switchSize.setChecked(false);
        else
            switchSize.setChecked(true);

        if(Global.level == 1)
            radioNivelGrupo.check(R.id.radioNivel1);
        if(Global.level == 2)
            radioNivelGrupo.check(R.id.radioNivel2);
        if(Global.level == 3)
            radioNivelGrupo.check(R.id.radioNivel3);

        if(Global.color == Color.GREEN)
            switchBlack.setChecked(false);
        else
            switchBlack.setChecked(true);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSoundMute = !isSoundMute;
                if (isSoundMute)
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up);

                //Saving status of volume button in Sharedprefs
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isSoundMute", isSoundMute);
                editor.apply();

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SoundEffects.Release();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.salir))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
