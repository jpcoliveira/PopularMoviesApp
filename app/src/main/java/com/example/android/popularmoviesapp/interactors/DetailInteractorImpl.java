package com.example.android.popularmoviesapp.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.network.MovieDetailAsyncTask;

/**
 * Created by joliveira on 5/3/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    private Context context;

    public DetailInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void findDetailMovie(OnFinishedListener listener, MovieModel movie) {
        MovieDetailAsyncTask detailAsyncTask = new MovieDetailAsyncTask(context, listener);
        detailAsyncTask.execute(movie);
    }
}
