package com.example.android.popularmoviesapp.interfaces.presenters;

import android.net.Uri;

import com.example.android.popularmoviesapp.model.MovieModel;

/**
 * Created by joliveira on 5/1/17.
 */

public interface DetailPresenter extends BasePresenter {
    String formatDate(String date);

    Uri buildURLTrailer(String key);

    MovieModel saveMovie(MovieModel movie);

    void findDetailMovie(MovieModel movie);
}
