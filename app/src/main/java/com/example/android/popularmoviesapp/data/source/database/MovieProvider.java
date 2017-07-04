package com.example.android.popularmoviesapp.data.source.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by joliveira on 6/17/17.
 */

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    private static final int REVIEW = 200;
    private static final int REVIEW_ID = 201;

    private static final int TRAILER = 300;
    private static final int TRAILER_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    /**
     * Builds a UriMatcher that is used to determine witch database request is being made.
     */
    public static UriMatcher buildUriMatcher() {
        String content = MovieContract.CONTENT_AUTHORITY;
        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
        matcher.addURI(content, MovieContract.PATH_REVIEW, REVIEW);
        matcher.addURI(content, MovieContract.PATH_REVIEW + "/#", REVIEW_ID);
        matcher.addURI(content, MovieContract.PATH_TRAILER, TRAILER);
        matcher.addURI(content, MovieContract.PATH_TRAILER + "/#", TRAILER_ID);

        return matcher;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return MovieContract.ReviewEntry.CONTENT_TYPE;
            case REVIEW_ID:
                return MovieContract.ReviewEntry.CONTENT_ITEM_TYPE;
            case TRAILER:
                return MovieContract.TrailerEntry.CONTENT_TYPE;
            case TRAILER_ID:
                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;

            case REVIEW:
                _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = MovieContract.ReviewEntry.buildReviewUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;

            case TRAILER:
                _id = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    returnUri = MovieContract.TrailerEntry.buildTrailerUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows; // Number of rows effected
        long _id = 0;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                rows = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                rows = db.delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry._ID + " = ?", new String[]{String.valueOf(_id)}
                );
                break;

            case REVIEW:
                rows = db.delete(MovieContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case REVIEW_ID:
                _id = ContentUris.parseId(uri);
                rows = db.delete(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        MovieContract.ReviewEntry._ID + " = ?", new String[]{String.valueOf(_id)}
                );
                break;

            case TRAILER:
                rows = db.delete(MovieContract.TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case TRAILER_ID:
                _id = ContentUris.parseId(uri);
                rows = db.delete(
                        MovieContract.TrailerEntry.TABLE_NAME,
                        MovieContract.TrailerEntry._ID + " = ?", new String[]{String.valueOf(_id)}
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because null could delete all rows:
        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                rows = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case REVIEW:
                rows = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case TRAILER:
                rows = db.update(MovieContract.TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;

        long _id = 0;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                retCursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID + " = ?", new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            case REVIEW:
                retCursor = db.query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case REVIEW_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        MovieContract.ReviewEntry._ID + " = ?", new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            case TRAILER:
                retCursor = db.query(
                        MovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case TRAILER_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        MovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        MovieContract.TrailerEntry._ID + " = ?", new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }
}
