package com.example.android.popularmoviesapp.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.network.MoviesAsyncTask;
import com.example.android.popularmoviesapp.util.Constants;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class HomeInteractorImpl implements HomeInteractor {
    @Override
    public void findMovies(OnFinishedListener listener, String filter, Context context) {
        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(context, listener);
        moviesAsyncTask.execute(filter);
    }

    @Override
    public void findMovies(OnFinishedListener listener, List<MovieModel> movies) {
        listener.onFinished(movies);
    }

    @Override
    public String getFilterMovies(int menuItemId) {
        switch (menuItemId) {
            case R.id.action_order_popular_movies:
                return Constants.POPULAR;
            case R.id.action_order_top_rated:
                return Constants.TOP_RATED;
            default:
                return Constants.POPULAR;
        }
    }
}
