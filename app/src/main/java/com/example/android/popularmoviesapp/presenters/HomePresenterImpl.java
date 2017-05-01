package com.example.android.popularmoviesapp.presenters;

import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.HomePresenter;
import com.example.android.popularmoviesapp.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class HomePresenterImpl implements HomePresenter, HomeInteractor.OnFinishedListener {
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onFinished(List<MovieModel> movies) {

    }
}
