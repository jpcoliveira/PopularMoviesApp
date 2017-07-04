package com.example.android.popularmoviesapp.domain.interactors;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviesapp.data.source.database.MovieContract;
import com.example.android.popularmoviesapp.data.model.MovieModel;
import com.example.android.popularmoviesapp.data.source.network.MovieDetailAsyncTask;
import com.example.android.popularmoviesapp.data.model.ReviewModel;
import com.example.android.popularmoviesapp.data.model.TrailerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/3/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    private static final String TAG = DetailInteractor.class.getSimpleName();
    private Context mContext;

    public DetailInteractorImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void findDetailMovie(OnFinishedListener listener, MovieModel movie) {

        MovieModel _movie = getFavoriteMovie(movie);

        if (_movie == null) {
            MovieDetailAsyncTask detailAsyncTask = new MovieDetailAsyncTask(mContext, listener);
            detailAsyncTask.execute(movie);
        } else {
            listener.onFinished(_movie);
        }
    }

    private MovieModel getFavoriteMovie(MovieModel movie) {

        List<ReviewModel> reviews;
        List<TrailerModel> trailers;

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.TrailerEntry.COLUMN_IDMOVIE + " = ?", new String[]{movie.getIdMovie()},
                null,
                null
        );

        MovieModel _movie = null;

        while (cursor.moveToNext()) {

            reviews = getReviewsBy(movie.getIdMovie());
            trailers = getTrailersBy(movie.getIdMovie());

            _movie = new MovieModel(
                    cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID_MOVIE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATERELEASE)),
                    trailers,
                    reviews,
                    cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_FAVORITE)) == 1

            );
        }
        cursor.close();

        return _movie;
    }

    @Override
    public MovieModel save(MovieModel movie) {

        List<ReviewModel> reviews;
        List<TrailerModel> trailers;

        try {
            MovieModel _movie = getFavoriteMovie(movie);

            if (_movie == null) {
                movie.setFavorite(true);
                reviews = movie.getReviews();
                trailers = movie.getTrailers();

                if (reviews != null) {
                    for (ReviewModel review : reviews) {
                        review.setIdMovie(movie.getIdMovie());
                        saveReview(review);
                    }

                    reviews = getReviewsBy(movie.getIdMovie());
                }

                if (trailers != null) {
                    for (TrailerModel trailer : trailers) {
                        trailer.setIdMovie(movie.getIdMovie());
                        saveTrailer(trailer);
                    }

                    trailers = getTrailersBy(movie.getIdMovie());
                }

                movie.setTrailers(trailers);
                movie.setReviews(reviews);
                saveFavoriteMovie(movie);
            } else {
                reviews = _movie.getReviews();
                trailers = _movie.getTrailers();

                if (reviews != null) {
                    for (ReviewModel review : reviews) {
                        deleteReview(review);
                    }
                }

                if (trailers != null) {
                    for (TrailerModel trailer : trailers) {
                        deleteTrailer(trailer);
                    }
                }

                deleteFavoriteMovie(_movie);
                movie.setFavorite(false);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return movie;
    }

    private List<TrailerModel> getTrailersBy(String idMovie) {
        List<TrailerModel> trailers = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.TrailerEntry.CONTENT_URI,
                null,
                MovieContract.TrailerEntry.COLUMN_IDMOVIE + " = ?", new String[]{idMovie},
                null
        );

        while (cursor.moveToNext()) {
            trailers.add(
                    new TrailerModel(
                            cursor.getLong(cursor.getColumnIndex(MovieContract.TrailerEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_ID_TRAILER)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_KEY)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_SITE)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_IDMOVIE))
                    )
            );
        }
        cursor.close();

        return trailers;
    }

    private List<ReviewModel> getReviewsBy(String idMovie) {
        List<ReviewModel> reviews = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.ReviewEntry.CONTENT_URI,
                null,
                MovieContract.ReviewEntry.COLUMN_IDMOVIE + " = ?", new String[]{idMovie},
                null
        );

        while (cursor.moveToNext()) {
            reviews.add(
                    new ReviewModel(
                            cursor.getLong(cursor.getColumnIndex(MovieContract.ReviewEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_ID_REVIEW)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_AUTHOR)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_CONTENT)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_URL)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_IDMOVIE))
                    )
            );
        }
        cursor.close();

        return reviews;
    }

    private void saveReview(ReviewModel review) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.ReviewEntry.COLUMN_ID_REVIEW, review.getIdReview());
        values.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
        values.put(MovieContract.ReviewEntry.COLUMN_CONTENT, review.getContent());
        values.put(MovieContract.ReviewEntry.COLUMN_URL, review.getUrl());
        values.put(MovieContract.ReviewEntry.COLUMN_IDMOVIE, review.getIdMovie());

        Uri uri = mContext.getContentResolver().insert(
                MovieContract.ReviewEntry.CONTENT_URI,
                values
        );

        Log.d(TAG, uri.toString());
    }

    private void saveTrailer(TrailerModel trailer) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.TrailerEntry.COLUMN_ID_TRAILER, trailer.getIdTrailer());
        values.put(MovieContract.TrailerEntry.COLUMN_KEY, trailer.getKey());
        values.put(MovieContract.TrailerEntry.COLUMN_NAME, trailer.getName());
        values.put(MovieContract.TrailerEntry.COLUMN_SITE, trailer.getSite());
        values.put(MovieContract.TrailerEntry.COLUMN_IDMOVIE, trailer.getIdMovie());

        Uri uri = mContext.getContentResolver().insert(
                MovieContract.TrailerEntry.CONTENT_URI,
                values
        );

        Log.d(TAG, uri.toString());
    }

    private void saveFavoriteMovie(MovieModel movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_ID_MOVIE, movie.getIdMovie());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_THUMBNAIL, movie.getThumbnail());
        values.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS, movie.getSynopsis());
        values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getRating());
        values.put(MovieContract.MovieEntry.COLUMN_DATERELEASE, movie.getDateRelease());
        values.put(MovieContract.MovieEntry.COLUMN_FAVORITE, movie.isFavorite());

        Uri uri = mContext.getContentResolver().insert(
                MovieContract.MovieEntry.CONTENT_URI,
                values
        );
        long id = ContentUris.parseId(uri);

        movie.set_id(id);

        Log.d(TAG, uri.toString());
    }

    private void deleteReview(ReviewModel review) {
        int rows = 0;
        rows = mContext.getContentResolver().delete(
                MovieContract.ReviewEntry.buildReviewUri(review.get_id()),
                null,
                null
        );
        Log.d(TAG, "delete review rows afected: " + rows);
    }

    private void deleteTrailer(TrailerModel trailer) {
        int rows = 0;
        rows = mContext.getContentResolver().delete(
                MovieContract.TrailerEntry.buildTrailerUri(trailer.get_id()),
                null,
                null
        );
        Log.d(TAG, "delete trailer rows afected: " + rows);
    }

    private void deleteFavoriteMovie(MovieModel movie) {
        int rows = 0;
        rows = mContext.getContentResolver().delete(
                MovieContract.MovieEntry.buildMovieUri(movie.get_id()),
                null,
                null
        );

        Log.d(TAG, "delete movie rows afected: " + rows);
    }
}
