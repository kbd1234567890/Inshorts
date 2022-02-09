package com.kanifanath.inshorts.database;

public class NewsDbSchema {

    public static final class NewsTable {
        public static final String tableName = "news";

        public static final class Cols{

            public static final String heading = "heading";
            public static final String detail = "detail";
            public static final String links = "links";
            public static final String linkWords = "linkWords";
            public static final String bitImage = "bitImage";
        }
    }
}
