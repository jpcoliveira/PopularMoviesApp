package com.example.android.popularmoviesapp.ui.presenters;

import android.os.Bundle;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.util.Util;
import com.example.android.popularmoviesapp.domain.interactors.MainInteractor;
import com.example.android.popularmoviesapp.ui.views.MainView;
import com.example.android.popularmoviesapp.data.model.MovieModel;
import com.example.android.popularmoviesapp.util.Constants;

import java.util.List;

/**
 * Created by joliveira on 5/1/17.
 */
public class MainPresenterImpl implements MainPresenter, MainInteractor.OnFinishedListener {

    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<MovieModel> movies) {
        if (mainView != null) {
            mainView.setMovieItem(movies);
            mainView.hideProgress();
        }
    }

    @Override
    public void onItemMenuClicked(int menuItemId) {
        if (mainView != null) {
            mainView.showProgress();
        }

        String filter = mainInteractor.getFilterMovies(menuItemId);

        if (Util.isOnline(mainView.getContextHomeView()) || filter.equals(Constants.FAVORITE)) {
            mainInteractor.findMovies(this, filter, mainView.getContextHomeView());
        } else {
            mainView.hideProgress();
            mainView.showMessageNoInternet();
        }
    }

    @Override
    public void findMovies(Bundle savedInstanceState) {
        if (mainView != null) {
            mainView.showProgress();
        }

        if (savedInstanceState != null) {
            List<MovieModel> movies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
            int intFilter = savedInstanceState.getInt(Constants.MENU);

            String filter = "";

            if (intFilter > 0) {
                filter = mainInteractor.getFilterMovies(intFilter);
            }

            if (Util.isOnline(mainView.getContextHomeView()) || filter.equals(Constants.FAVORITE)) {
                mainInteractor.findMovies(this, filter, movies);
            } else {
                mainView.hideProgress();
                mainView.showMessageNoInternet();
            }

        } else {
            if (Util.isOnline(mainView.getContextHomeView())) {
                //first search by popular
                mainInteractor.findMovies(this,
                        mainInteractor.getFilterMovies(R.id.action_order_popular_movies),
                        mainView.getContextHomeView());
            } else {
                mainView.hideProgress();
                mainView.showMessageNoInternet();
            }
        }

    }
}
