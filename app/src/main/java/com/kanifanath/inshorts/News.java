package com.kanifanath.inshorts;

import android.content.ContentValues;
import android.graphics.Bitmap;

import com.kanifanath.inshorts.database.NewsDbSchema;

public class News {

    String heading, detail, links, linkWords;
    Bitmap bitImage;

    public News(String heading, String detail, Bitmap bitImage, String links, String linkWords){
        this.heading = heading;
        this.detail = detail;
        this.bitImage = bitImage;
        this.links = links;
        this.linkWords = linkWords;
    }


}
