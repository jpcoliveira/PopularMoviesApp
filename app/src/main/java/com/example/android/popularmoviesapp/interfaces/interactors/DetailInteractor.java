package com.example.android.popularmoviesapp.interfaces.interactors;

import com.example.android.popularmoviesapp.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/3/17.
 */

public interface DetailInteractor {

    interface OnFinishedListener {
        void onFinished(MovieModel movies);
    }

    void findDetailMovie(OnFinishedListener listener, MovieModel movie);

    MovieModel save(MovieModel movie);

}
