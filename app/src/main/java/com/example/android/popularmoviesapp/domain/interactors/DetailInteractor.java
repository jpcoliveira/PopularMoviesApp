package com.example.android.popularmoviesapp.domain.interactors;

import com.example.android.popularmoviesapp.data.model.MovieModel;

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
