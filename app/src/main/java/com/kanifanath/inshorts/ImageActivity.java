package com.kanifanath.inshorts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

public class ImageActivity extends AppCompatActivity  {

    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private ImageView imageView;
    private ImageButton imageButton;
    public static String key = "com.kanifanath.inshorts.Bitmap";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        byte byteArray [] = getIntent().getByteArrayExtra(key);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        imageView = (ImageView)findViewById(R.id.fullImageId);
        imageView.setImageBitmap(bitmap);
        scaleGestureDetector = new ScaleGestureDetector(this, new EventListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class EventListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor,10.0f));
            imageView.setScaleX(scaleFactor);
            imageView.setScaleY(scaleFactor);
            return true;
        }
    }
}
