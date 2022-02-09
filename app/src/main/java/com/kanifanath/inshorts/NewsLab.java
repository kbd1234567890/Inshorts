package com.kanifanath.inshorts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.kanifanath.inshorts.database.NewsBaseHelper;
import com.kanifanath.inshorts.database.NewsCursorWrapper;
import com.kanifanath.inshorts.database.NewsDbSchema;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewsLab {

    private static Context context;
    private static NewsLab newsLab;

    private SQLiteDatabase sqLiteDatabase;

    private NewsLab(Context mcontext){

        this.context = mcontext.getApplicationContext();
        sqLiteDatabase = new NewsBaseHelper(context).getWritableDatabase();
    }

    public static NewsLab get(Context appContext){

        if(newsLab == null){
            newsLab = new NewsLab(appContext.getApplicationContext());
        }
        return newsLab;
    }
    private static ContentValues getContentValues(News news) {

        ContentValues values = new ContentValues();
        values.put(NewsDbSchema.NewsTable.Cols.heading, news.heading);
        values.put(NewsDbSchema.NewsTable.Cols.detail, news.detail);
        values.put(NewsDbSchema.NewsTable.Cols.links, news.links);
        values.put(NewsDbSchema.NewsTable.Cols.linkWords, news.linkWords);
        values.put(NewsDbSchema.NewsTable.Cols.bitImage, toByteArrayOfImage(news.bitImage));

        return values;
    }

    private static byte[] toByteArrayOfImage(Bitmap bitmap){
        byte byteArray[];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byteArray = stream.toByteArray();

        return byteArray;
    }

    public void addNews(News news){

        ContentValues values = getContentValues(news);

        sqLiteDatabase.insert(NewsDbSchema.NewsTable.tableName, null, values);

    }

    public void deleteNews(News news){
        if (news != null) {
            String headingOfnews = news.heading;
            sqLiteDatabase.delete(NewsDbSchema.NewsTable.tableName, NewsDbSchema.NewsTable.Cols.heading
                    + " = ?", new String[]{headingOfnews});
        }

    }

    public NewsCursorWrapper queryNews(String whereClause, String [] whereArgs){

        Cursor cursor = sqLiteDatabase.query(NewsDbSchema.NewsTable.tableName,null,whereClause,
                whereArgs,null,null,null);
        return new NewsCursorWrapper(cursor);
    }
    public List<News> getAllNews() {

        List<News> newsList = new ArrayList<>();

        NewsCursorWrapper newsCursorWrapper = queryNews(null, null);
        try{
            newsCursorWrapper.moveToFirst();
            while(!newsCursorWrapper.isAfterLast()){
                newsList.add(newsCursorWrapper.getNews());
                newsCursorWrapper.moveToNext();
            }
        }
        finally {
            newsCursorWrapper.close();
        }
        return newsList;
    }

    public News getNews(String heading) {

        NewsCursorWrapper cursor = queryNews(NewsDbSchema.NewsTable.Cols.heading
                + " = ? " , new String[]{heading});

        try{
            if(cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getNews();
        }
        finally {
            cursor.close();
        }
    }

}
