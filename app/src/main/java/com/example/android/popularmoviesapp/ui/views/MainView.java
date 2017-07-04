package com.example.android.popularmoviesapp.ui.views;

import android.content.Context;

import com.example.android.popularmoviesapp.data.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public interface MainView {

    void showProgress();

    void hideProgress();

    void setMovieItem(List<MovieModel> movies);

    Context getContextHomeView();

    void showMessageNoInternet();
}
