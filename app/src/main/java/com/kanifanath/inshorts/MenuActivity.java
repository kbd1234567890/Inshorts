package com.kanifanath.inshorts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity {

    private CardView bookmark, business, sports, india, tech, myFeed;
    private String url;
    public static String key = "com.kanifanath.Inshorts.MenuActivity";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);

        bookmark = (CardView)findViewById(R.id.bookmarkId);
        business = (CardView)findViewById(R.id.businessId);
        sports = (CardView)findViewById(R.id.sportsId);
        india = (CardView)findViewById(R.id.indiaId);
        tech = (CardView)findViewById(R.id.techId);
        myFeed = (CardView)findViewById(R.id.feedId);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Bookmarked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, "bookmark");
                startActivity(intent);
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://inshorts.com/en/read/business";
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, url);
                startActivity(intent);
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://inshorts.com/en/read/technology";
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, url);
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://inshorts.com/en/read/sports";
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, url);
                startActivity(intent);
            }
        });

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://inshorts.com/en/read/national";
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, url);
                startActivity(intent);
            }
        });

        myFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://inshorts.com/en/read/";
                Intent intent = new Intent(MenuActivity.this, ViewPagerActivity.class);
                intent.putExtra(key, url);
                startActivity(intent);
                finish();
            }
        });
    }
}
