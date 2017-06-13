package com.example.android.popularmoviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.adapters.MoviesAdapter;
import com.example.android.popularmoviesapp.domain.util.Constants;
import com.example.android.popularmoviesapp.interactors.MainInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.listeners.MoviesAdapterOnClickHandler;
import com.example.android.popularmoviesapp.interfaces.presenters.MainPresenter;
import com.example.android.popularmoviesapp.interfaces.views.MainView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.presenters.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/21/17.
 */

public class MainFragment extends Fragment implements MainView, MoviesAdapterOnClickHandler {

    private MainPresenter presenter;
    private MainInteractorImpl interactor;
    private RecyclerView recyclerView;
    ProgressBar progress;
    MoviesAdapter adapter;
    TextView textViewErrorNoData;
    private int idMenuActive;
    private Bundle mSavedInstanceState;
    Context mContext;

    public interface Callback {
        public void onItemSelected(MovieModel movie);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        adapter = new MoviesAdapter(this);
        interactor = new MainInteractorImpl();
        presenter = new MainPresenterImpl(this, interactor);
        textViewErrorNoData = (TextView) view.findViewById(R.id.tv_msg_no_data);
        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_movies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        mSavedInstanceState = savedInstanceState;
        presenter.findMovies(savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void clickItemListener(MovieModel movie) {
        ((Callback) getActivity()).onItemSelected(movie);
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

    @Override
    public Context getContextHomeView() {
        return mContext;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<MovieModel> movies = (ArrayList<MovieModel>) adapter.getMovies();
        outState.putParcelableArrayList(Constants.MOVIES, movies);
        outState.putInt(Constants.MENU, idMenuActive);
        mSavedInstanceState = outState;
    }

/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        presenter.onItemMenuClicked(id);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle().toString());
        idMenuActive = id;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            presenter.findMovies(mSavedInstanceState);
        }
    }
}
