package com.example.android.popularmoviesapp.interfaces.interactors;

import com.example.android.popularmoviesapp.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public interface HomeInteractor {

    interface OnFinishedListener {
        void onFinished(List<MovieModel> movies);
    }

    void findMovies(OnFinishedListener listener);
}
