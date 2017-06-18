package com.example.android.popularmoviesapp.domain.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joliveira on 6/17/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    /**
     * Defines the database version. This variable must be incremented in order for onUpdate to
     * be called when necessary.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * The name of the database on the device.
     */
    private static final String DATABASE_NAME = "popularmovie.db";

    /**
     * Default constructor.
     *
     * @param context The application context using this database.
     */
    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is first created.
     *
     * @param db The database being created, which all SQL statements will be executed on.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        addMovieTable(db);
        addReviewTable(db);
        addTrailerTable(db);
    }

    /**
     * Called whenever DATABASE_VERSION is incremented. This is used whenever schema changes need
     * to be made or new tables are added.
     *
     * @param db         The database being updated.
     * @param oldVersion The previous version of the database. Used to determine whether or not
     *                   certain updates should be run.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void addMovieTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                        MovieContract.MovieEntry.COLUMN_ID_MOVIE + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_THUMBNAIL + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_DATERELEASE + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_FAVORITE + " TEXT NULL, " +
                        MovieContract.MovieEntry.COLUMN_RATING + " TEXT NULL );"
        );
    }

    private void addReviewTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.ReviewEntry.TABLE_NAME + " (" +
                        MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY, " +
                        MovieContract.ReviewEntry.COLUMN_ID_REVIEW + " TEXT NULL, " +
                        MovieContract.ReviewEntry.COLUMN_URL + " TEXT NULL, " +
                        MovieContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NULL, " +
                        MovieContract.ReviewEntry.COLUMN_CONTENT + " TEXT NULL, " +
                        MovieContract.ReviewEntry.COLUMN_IDMOVIE + " TEXT NULL, " +
                        "FOREIGN KEY (" + MovieContract.ReviewEntry.COLUMN_IDMOVIE + ") " +
                        "REFERENCES "
                        + MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry.COLUMN_ID_MOVIE + "));"
        );
    }

    private void addTrailerTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + MovieContract.TrailerEntry.TABLE_NAME + " (" +
                        MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY, " +
                        MovieContract.TrailerEntry.COLUMN_NAME + " TEXT NULL, " +
                        MovieContract.TrailerEntry.COLUMN_ID_TRAILER + " TEXT NULL, " +
                        MovieContract.TrailerEntry.COLUMN_KEY + " TEXT NULL, " +
                        MovieContract.TrailerEntry.COLUMN_SITE + " TEXT NULL, " +
                        MovieContract.TrailerEntry.COLUMN_IDMOVIE + " TEXT NULL, " +
                        "FOREIGN KEY (" + MovieContract.TrailerEntry.COLUMN_IDMOVIE + ") " +
                        "REFERENCES "
                        + MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry.COLUMN_ID_MOVIE + "));"
        );
    }

}
