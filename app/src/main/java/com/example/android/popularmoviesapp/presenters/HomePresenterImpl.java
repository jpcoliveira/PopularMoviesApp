package com.example.android.popularmoviesapp.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.HomePresenter;
import com.example.android.popularmoviesapp.interfaces.views.HomeView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */
public class HomePresenterImpl implements HomePresenter, HomeInteractor.OnFinishedListener {

    private HomeView homeView;
    private HomeInteractor homeInteractor;

    public HomePresenterImpl(HomeView homeView, HomeInteractor homeInteractor) {
        this.homeView = homeView;
        this.homeInteractor = homeInteractor;
    }

    @Override
    public void onResume() {
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

    @Override
    public void onItemMenuClicked(int menuItemId) {
        homeInteractor.getFilterMovies(menuItemId);
        homeInteractor.findMovies(this, homeInteractor.getFilterMovies(menuItemId), homeView.getContextHomeView());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState, Bundle arguments) {
        if (homeView != null) {
            homeView.showProgress();
        }

        if (savedInstanceState != null) {
            List<MovieModel> movies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
            homeInteractor.findMovies(this, movies);
            Log.i("call service", "no");
        } else {
            //first search by popular
            homeInteractor.findMovies(this, homeInteractor.getFilterMovies(R.id.action_order_popular_movies), homeView.getContextHomeView());
            Log.i("call service", "yes");
        }
    }
}
