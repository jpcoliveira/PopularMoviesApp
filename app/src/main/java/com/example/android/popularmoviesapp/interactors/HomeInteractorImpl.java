package com.example.android.popularmoviesapp.interactors;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.network.MoviesAsyncTask;
import com.example.android.popularmoviesapp.util.Constants;

/**
 * Created by joliveira on 5/1/17.
 */

public class HomeInteractorImpl implements HomeInteractor {
    @Override
    public void findMovies(OnFinishedListener listener, String filter) {
        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(listener);
        moviesAsyncTask.execute(filter);
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
