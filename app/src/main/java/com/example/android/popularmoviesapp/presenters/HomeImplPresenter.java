package com.example.android.popularmoviesapp.presenters;

import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.HomePresenter;
import com.example.android.popularmoviesapp.interfaces.views.HomeView;
import com.example.android.popularmoviesapp.model.MovieModel;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */

public class HomeImplPresenter implements HomePresenter, HomeInteractor.OnFinishedListener {

    private HomeView homeView;
    private HomeInteractor homeInteractor;

    public HomeImplPresenter(HomeView homeView, HomeInteractor homeInteractor) {
        this.homeView = homeView;
        this.homeInteractor = homeInteractor;
    }

    @Override
    public void onResume() {

        if (homeView != null) {
            homeView.showProgress();
        }
        homeInteractor.findMovies(this);
    }

    @Override
    public void onDestroy() {
        homeView = null;
    }

    @Override
    public void onFinished(List<MovieModel> movies) {
        if (homeView != null) {
            homeView.setMovieItem(movies);
            homeView.hideProgress();
        }
    }

    @Override
    public void onIntemClicked(int position) {
        if (homeView != null) {
            homeView.showMessageItemClicked("item clicado foi: " + position);
        }
    }
}
