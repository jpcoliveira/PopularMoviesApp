package com.example.android.popularmoviesapp.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.DetailPresenter;
import com.example.android.popularmoviesapp.interfaces.views.DetailView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.util.Constants;

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

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState, Bundle arguments) {
        MovieModel movie = arguments.getParcelable(Constants.MOVIE);

        if (movie != null)
            interactor.findDetailMovie(this, movie);
    }

    @Override
    public void onFinished(MovieModel movies) {
        if (detailView != null) {
            detailView.setItemDetail(movies);
        }
    }
}
