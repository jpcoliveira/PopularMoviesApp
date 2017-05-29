package com.example.android.popularmoviesapp.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.interactors.MainInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.network.MoviesAsyncTask;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class MainInteractorImpl implements MainInteractor {
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
        List<MovieModel> movies = MovieModel.listAll(MovieModel.class);
        if (movies != null) {
            listener.onFinished(movies);
        }
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