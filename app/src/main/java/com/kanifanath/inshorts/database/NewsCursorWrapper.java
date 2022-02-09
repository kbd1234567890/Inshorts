package com.kanifanath.inshorts.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.kanifanath.inshorts.News;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class NewsCursorWrapper extends CursorWrapper {

    Cursor objCursor;
    public NewsCursorWrapper(Cursor cursor){
        super(cursor);
        objCursor = cursor;
    }

    public News getNews(){

        String headingString = objCursor.getString(getColumnIndex(NewsDbSchema.NewsTable.Cols.heading));
        String detailString = objCursor.getString(getColumnIndex(NewsDbSchema.NewsTable.Cols.detail));
        String linksString = objCursor.getString(getColumnIndex(NewsDbSchema.NewsTable.Cols.links));
        String linkWordsString = objCursor.getString(getColumnIndex(NewsDbSchema.NewsTable.Cols.linkWords));
        byte []bitImage = objCursor.getBlob(getColumnIndex(NewsDbSchema.NewsTable.Cols.bitImage));

        News news = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitImage,0,bitImage.length);
            news = new News(headingString, detailString, bitmap, linksString,
                    linkWordsString);
            if (bitmap == null)
                Log.d("NewsCursorWrapper", "Null Bitmap" + bitImage.length);
        }
        catch (Exception e){
            e.getMessage();
            return null;
        }
        return news;
    }
}
