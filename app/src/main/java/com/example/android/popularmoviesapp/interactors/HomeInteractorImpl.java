package com.example.android.popularmoviesapp.interactors;

import android.os.Handler;

import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.network.MoviesAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class HomeInteractorImpl implements HomeInteractor {
    @Override
    public void findMovies(OnFinishedListener listener) {
        MoviesAsyncTask moviesAsyncTask = new MoviesAsyncTask(listener);
        moviesAsyncTask.execute("");
    }
}
