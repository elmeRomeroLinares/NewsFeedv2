package com.example.readmenewsfeedapp.data;

import android.provider.BaseColumns;

public class NewsContract {

    // private constructor to avoid instantiation
    private NewsContract(){}

    public static final class NewsEntry implements BaseColumns {

        // table savedNews name constant
        private static final String TABLE_NAME = "savedNews";

        // table savedNews columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ARTICLE_HEADLINE = "headline";
        public static final String COLUMN_ARTICLE_SECTION = "section";
        public static final String COLUMN_ARTICLE_THUMBNAIL = "thumbnail";
        public static final String COLUMN_ARTICLE_BODY = "body";
    }


}
