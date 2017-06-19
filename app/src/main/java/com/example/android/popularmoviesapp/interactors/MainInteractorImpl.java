package com.example.android.popularmoviesapp.interactors;

import android.content.Context;
import android.database.Cursor;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.database.MovieContract;
import com.example.android.popularmoviesapp.interfaces.interactors.MainInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.network.MoviesAsyncTask;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class MainInteractorImpl implements MainInteractor {

    private Context mContext;

    public MainInteractorImpl(Context context) {
        mContext = context;
    }

    @Override
    public void findMovies(OnFinishedListener listener, String filter, Context context) {

        if (filter.equals(Constants.FAVORITE)) {
            getFavoriteMovies(listener);
        } else {
            MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(context, listener);
            moviesAsyncTask.execute(filter);
        }
    }

    void getFavoriteMovies(OnFinishedListener listener) {

        List<MovieModel> movies = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            movies.add(
                    new MovieModel(
                            cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID_MOVIE)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMBNAIL)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)),
                            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATERELEASE)),
                            null,
                            null,
                            cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_FAVORITE)) == 1
                    )
            );
        }

        cursor.close();

        listener.onFinished(movies);

    }

    @Override
    public void findMovies(OnFinishedListener listener, String filter, List<MovieModel> movies) {
        if (filter == Constants.FAVORITE) {
            getFavoriteMovies(listener);
        } else {
            listener.onFinished(movies);
        }
    }

    @Override
    public String getFilterMovies(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_order_popular_movies:
                return Constants.POPULAR;
            case R.id.action_order_top_rated:
                return Constants.TOP_RATED;
            case R.id.action_order_favorite:
                return Constants.FAVORITE;
            default:
                return Constants.POPULAR;
        }
    }
}
