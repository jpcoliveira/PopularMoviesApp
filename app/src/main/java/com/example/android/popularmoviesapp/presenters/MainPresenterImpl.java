package com.example.android.popularmoviesapp.presenters;

import android.os.Bundle;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interfaces.interactors.MainInteractor;
import com.example.android.popularmoviesapp.interfaces.presenters.MainPresenter;
import com.example.android.popularmoviesapp.interfaces.views.MainView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.util.Constants;

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
        mainInteractor.findMovies(this, mainInteractor.getFilterMovies(menuItemId), mainView.getContextHomeView());
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

            mainInteractor.findMovies(this, filter, movies);

        } else {
            //first search by popular
            mainInteractor.findMovies(this,
                    mainInteractor.getFilterMovies(R.id.action_order_popular_movies),
                    mainView.getContextHomeView());
        }
    }
}
