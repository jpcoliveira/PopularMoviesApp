package com.example.android.popularmoviesapp.ui.presenters;

import android.net.Uri;

import com.example.android.popularmoviesapp.data.model.MovieModel;

/**
 * Created by joliveira on 5/1/17.
 */

public interface DetailPresenter extends BasePresenter {
    String formatDate(String date);

    Uri buildURLTrailer(String key);

    void saveMovie(MovieModel movie);

    void findDetailMovie(MovieModel movie);
}
