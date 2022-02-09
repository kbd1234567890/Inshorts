package com.kanifanath.inshorts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerActivity extends AppCompatActivity implements NewsFragment.Callback{

    private ViewPager viewPager, tempViewPager;
    private List<News> totalNews = new ArrayList<>();
    private String TAG = "ViewPagerActivity";
    private String urlMenu;

    @Override
    public void imageSelected(int position){

        byte byteArray[];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = totalNews.get(position).bitImage;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byteArray = stream.toByteArray();
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(ImageActivity.key,byteArray);
        startActivity(intent);
    }
    @Override
    public void linkSelected(int position){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(totalNews.get(position).links));
        startActivity(intent);
    }

    @Override
    public void menuSelected() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        Log.d("ViewPagerActivity", "In ViewPagerActivity");

        if(getIntent() != null){

            urlMenu = getIntent().getStringExtra(MenuActivity.key);
            if(urlMenu == null){
                urlMenu = "https://inshorts.com/en/read/";
            }
        }

        tempViewPager = (ViewPager)findViewById(R.id.fragmentContainer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        tempViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){


            @Override
            public Fragment getItem(int position) {
                return ProgressFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        new Fetch().execute();
    }
    private class Fetch extends AsyncTask<Void, Void, Void>{
        private String word;
        private String TAG = "Fetch";

        private List<Bitmap> handleImages(Elements allImages) {
            String src[] = new String[allImages.size()];

            for(int i = 0; i < allImages.size(); i++){
                src[i] = allImages.get(i).attr("style");
                src[i] = src[i].substring(src[i].indexOf('\'') + 1,src[i].lastIndexOf('\''));
            }

            List<Bitmap> bitImages = new ArrayList<>();
            URL url = null;
            try {

                for (int i = 0; i < allImages.size(); i++) {
                    url = new URL(src[i]);
                    InputStream in = url.openStream();
                    bitImages.add(BitmapFactory.decodeStream(in));
                    in.close();
                }
            }
            catch (Exception mal){
                mal.printStackTrace();
            }
            return bitImages;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if(urlMenu.equals("bookmark")) {
                totalNews = NewsLab.get(ViewPagerActivity.this).getAllNews();
            }
            else {
                try {
                    Document doc = Jsoup.connect(urlMenu).get();

                    Elements allDescription = doc.getElementsByAttributeValue("itemprop", "articleBody");
                    List<String> details = allDescription.eachText();

                    Elements allHeadings = doc.getElementsByAttributeValue("itemprop", "headline");
                    List<String> headings = allHeadings.eachText();

                    Elements allImages = doc.getElementsByAttributeValueContaining("style", "background-image: url(");
                    List<Bitmap> bitImages = handleImages(allImages);

                    Elements allLinks = doc.getElementsByAttributeValue("class", "source");
                    List<String> linkWords = allLinks.eachText();
                    List<String> links = allLinks.eachAttr("href");

                    Log.d(TAG, allImages.size() + "");

                    for (int i = 0; i < (details.size() < links.size() ? details.size() : links.size())
                            ; i++) {

                        //Log.i(TAG, links.get(i) + " : " + linkWords.get(i));
                        totalNews.add(new News(headings.get(i), details.get(i), bitImages.get(i)
                                , links.get(i), linkWords.get(i)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager = (ViewPager)findViewById(R.id.fragmentContainer);
            FragmentManager fragmentManager = getSupportFragmentManager();
            viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                @NonNull
                @Override
                public Fragment getItem(int position) {

                    return NewsFragment.newInstance(totalNews, position);
                }

                @Override
                public int getCount() {
                    return totalNews.size();
                }
            });
        }
    }
}
