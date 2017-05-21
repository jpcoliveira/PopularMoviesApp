package com.example.android.popularmoviesapp.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.adapters.MoviesAdapter;
import com.example.android.popularmoviesapp.interactors.MainInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.listeners.MoviesAdapterOnClickHandler;
import com.example.android.popularmoviesapp.interfaces.presenters.MainPresenter;
import com.example.android.popularmoviesapp.interfaces.views.MainView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.presenters.MainPresenterImpl;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 4/28/17.
 */

public class MainActivity extends AppCompatActivity implements MainView, MoviesAdapterOnClickHandler {

    private MainPresenter mainPresenter;
    private MainInteractorImpl homeInteractor;
    private RecyclerView recyclerView;
    ProgressBar progress;
    MoviesAdapter adapter;
    TextView textViewErrorNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MoviesAdapter(this);
        homeInteractor = new MainInteractorImpl();
        mainPresenter = new MainPresenterImpl(this, homeInteractor);
        textViewErrorNoData = (TextView) findViewById(R.id.tv_msg_no_data);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_movies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progress = (ProgressBar) findViewById(R.id.progress);

        mainPresenter.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovieItem(List<MovieModel> movies) {
        if (movies != null && movies.size() > 0) {
            adapter.setMovies(movies);
            textViewErrorNoData.setVisibility(View.GONE);
        } else {
            adapter.setMovies(null);
            textViewErrorNoData.setVisibility(View.VISIBLE);
        }
    }

    ///// TODO: 5/2/17 incluir readme ocm explicacoes referente como obter a chave apikey da api de videos
    @Override
    public Context getContextHomeView() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mainPresenter.onItemMenuClicked(item.getItemId());
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<MovieModel> movies = (ArrayList<MovieModel>) adapter.getMovies();
        outState.putParcelableArrayList(Constants.MOVIES, movies);
    }


    @Override
    public void clickItemListener(MovieModel movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.MOVIE, movie);
        startActivity(intent);
    }
}
