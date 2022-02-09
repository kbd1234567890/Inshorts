package com.kanifanath.inshorts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.splash_screen);
        imageView = (ImageView)findViewById(R.id.splashImageId);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                finish();

            }
        };
        new Handler().postDelayed(r,2000);
    }
}
