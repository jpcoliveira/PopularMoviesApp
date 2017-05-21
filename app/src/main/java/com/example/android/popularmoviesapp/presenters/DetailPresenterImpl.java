package com.example.android.popularmoviesapp.presenters;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.util.Util;
import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.DetailPresenter;
import com.example.android.popularmoviesapp.interfaces.views.DetailView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joliveira on 5/1/17.
 */

public class DetailPresenterImpl implements DetailPresenter, DetailInteractor.OnFinishedListener {

    private DetailView detailView;
    private DetailInteractor interactor;

    public DetailPresenterImpl(DetailView detailView, DetailInteractor interactor) {
        this.detailView = detailView;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        detailView = null;
    }

    @Override
    public void onFinished(MovieModel movies) {
        if (detailView != null) {
            detailView.setItemDetail(movies);
            detailView.hideProgress();
        }
    }

    @Override
    public String formatDate(String date) {
        String year = "";

        if (!date.isEmpty())
            year = date.split("-")[0];

        return year;
    }

    @Override
    public Uri buildURLTrailer(String key) {

        Uri uri;
        HashMap<String, String> parameter = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();

        paths.add(Constants.URL_PATH_YOUTUBE);
        parameter.put(Constants.URL_QUERY_YOUTUBE, key);
        uri = Util.buildUri(Constants.URL_YOUTUBE, parameter, paths);

        return uri;
    }

    @Override
    public MovieModel saveMovie(MovieModel movie) {

        try {
            movie = interactor.save(movie);

            if (movie.isFavorite()) {
                detailView.showMessage(detailView.getContextView().getString(R.string.success_save));
            } else {
                detailView.showMessage(detailView.getContextView().getString(R.string.success_delete));
            }
            return movie;

        } catch (Exception ex) {
            detailView.showMessage(ex.getMessage());
            return null;
        }
    }

    @Override
    public void findDetailMovie(MovieModel movie) {
        if (movie != null) {

            if (detailView != null) {
                detailView.showProgress();
            }

            interactor.findDetailMovie(this, movie);
        }
    }
}
