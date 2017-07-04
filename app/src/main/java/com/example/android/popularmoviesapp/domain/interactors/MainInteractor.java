package com.example.android.popularmoviesapp.domain.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.data.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public interface MainInteractor {

    interface OnFinishedListener {
        void onFinished(List<MovieModel> movies);
    }

    void findMovies(OnFinishedListener listener, String filter, Context context);

    void findMovies(OnFinishedListener listener, String filter, List<MovieModel> movies);

    String getFilterMovies(int menuItemId);
}
