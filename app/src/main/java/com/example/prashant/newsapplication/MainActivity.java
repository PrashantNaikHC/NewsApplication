package com.example.prashant.newsapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = (TextView) findViewById(R.id.title);
        TextView sub_title = (TextView) findViewById(R.id.sub_title);

        Typeface typefaceEquality = Typeface.createFromAsset(getAssets(),"fonts/Equality.ttf");
        Typeface typefaceHeptal = Typeface.createFromAsset(getAssets(),"fonts/Heptal-Book.ttf");
        Typeface typefacePotra = Typeface.createFromAsset(getAssets(),"fonts/Potra.ttf");
        Typeface summer = Typeface.createFromAsset(getAssets(),"fonts/Summer.ttf");



        //title.setTypeface(summer);
        sub_title.setTypeface(typefaceEquality);
        sub_title.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        sub_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent NewsIntent = new Intent(MainActivity.this,NewsActivity.class);
                startActivity(NewsIntent);
            }
        });
    }
}
