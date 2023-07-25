package com.project.quiznew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

    ImageView splashimg;
    TextView textsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        splashimg=findViewById(R.id.splahs);
        textsplash=findViewById(R.id.textViewsplash);


        Animation anime= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashanim);
        splashimg.startAnimation(anime);
        textsplash.startAnimation(anime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splashscreen.this,loginpg.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
}