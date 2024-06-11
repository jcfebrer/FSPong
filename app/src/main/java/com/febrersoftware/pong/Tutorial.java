package com.febrersoftware.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Tutorial extends AppCompatActivity {

    TextView tv_guideTitle, tv_title_one,tv_title_two,tv_title_three, tv_paragraph_one,tv_paragraph_two,tv_paragraph_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        tv_guideTitle = (TextView) findViewById(R.id.guideTitle);
        //titles
        tv_title_one = (TextView) findViewById(R.id.tv_title1);
        tv_title_two = (TextView) findViewById(R.id.tv_title2);
        tv_title_three = (TextView) findViewById(R.id.tv_title3);
        //paragraphs
        tv_paragraph_one = (TextView) findViewById(R.id.tv_paragraph1);
        tv_paragraph_two = (TextView) findViewById(R.id.tv_paragraph2);
        tv_paragraph_three = (TextView) findViewById(R.id.tv_paragraph3);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(),
                "fonts/PressStart2PRegular.ttf");

        //Typeface typeface2 = Typeface.createFromAsset(getAssets(),
        //       "fonts/AliceRegular.ttf");

        tv_guideTitle.setTypeface(typeface1);

        tv_title_one.setTypeface(typeface1);
        tv_title_two.setTypeface(typeface1);
        tv_title_three.setTypeface(typeface1);

        tv_paragraph_one.setTypeface(typeface1);
        tv_paragraph_two.setTypeface(typeface1);
        tv_paragraph_three.setTypeface(typeface1);

        String html1 = getString(R.string.tv_text1);
        String html2 = getString(R.string.tv_text2);
        String html3 = getString(R.string.tv_text3);


        String result1 = html1.replace("<b>","<font color=\"#332FA2\"><b>").replace("</b>", "</b></font>");
        String result2 = html2.replace("<b>","<font color=\"#332FA2\"><b>").replace("</b>", "</b></font>");
        String result3 = html3.replace("<b>","<font color=\"#332FA2\"><b>").replace("</b>", "</b></font>");
        tv_paragraph_one.setText(Html.fromHtml(result1));
        tv_paragraph_two.setText(Html.fromHtml(result2));
        tv_paragraph_three.setText(Html.fromHtml(result3));

    }
}
