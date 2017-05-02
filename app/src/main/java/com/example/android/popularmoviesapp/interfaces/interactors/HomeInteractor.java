package com.example.android.popularmoviesapp.interfaces.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public interface HomeInteractor {

    interface OnFinishedListener {
        void onFinished(List<MovieModel> movies);
    }

    void findMovies(OnFinishedListener listener, String filter, Context context);

    void findMovies(OnFinishedListener listener, List<MovieModel> movies);

    String getFilterMovies(int menuItemId);
}
