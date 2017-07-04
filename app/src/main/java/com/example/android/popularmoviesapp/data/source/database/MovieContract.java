package com.example.android.popularmoviesapp.data.source.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.popularmoviesapp.BuildConfig;

/**
 * Created by joliveira on 6/17/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;//"com.example.android.popularmoviesapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_TRAILER = "trailer";

    public static final class MovieEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;

        // Define the table schema
        public static final String TABLE_NAME = "movieTable";

        public static final String COLUMN_ID_MOVIE = "idMovie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATERELEASE = "dateRelease";
        public static final String COLUMN_FAVORITE = "favorite";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "reviewTable";

        public static final String COLUMN_ID_REVIEW = "idReview";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IDMOVIE = "idMovie";

        public static Uri buildReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TrailerEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_TRAILER;

        public static final String TABLE_NAME = "trailerTable";

        public static final String COLUMN_ID_TRAILER = "idTrailer";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_IDMOVIE = "idMovie";

        public static Uri buildTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
