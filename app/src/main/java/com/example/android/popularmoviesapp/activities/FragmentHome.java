package com.example.android.popularmoviesapp.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.adapters.MoviesAdapter;
import com.example.android.popularmoviesapp.interactors.HomeInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.listeners.MoviesAdapterOnClickHandler;
import com.example.android.popularmoviesapp.interfaces.presenters.HomePresenter;
import com.example.android.popularmoviesapp.interfaces.views.HomeView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.presenters.HomePresenterImpl;
import com.example.android.popularmoviesapp.domain.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 4/28/17.
 */

public class FragmentHome extends Fragment implements HomeView, MoviesAdapterOnClickHandler {

    private HomePresenter homePresenter;
    private HomeInteractorImpl homeInteractor;
    private RecyclerView recyclerView;
    ProgressBar progress;
    MoviesAdapter adapter;
    TextView textViewErrorNoData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        adapter = new MoviesAdapter(this);
        homeInteractor = new HomeInteractorImpl();
        homePresenter = new HomePresenterImpl(this, homeInteractor);
        textViewErrorNoData = (TextView) view.findViewById(R.id.tv_msg_no_data);
        RecyclerView.LayoutManager manager = new GridLayoutManager(view.getContext(), 2);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_movies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        homePresenter.onCreateView(savedInstanceState, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        homePresenter.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        homePresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
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
        if (movies != null) {
            adapter.setMovies(movies);
            textViewErrorNoData.setVisibility(View.GONE);
        } else {
            adapter.setMovies(null);
            textViewErrorNoData.setVisibility(View.VISIBLE);
        }
    }

    ///// TODO: 5/2/17 incluir readme ocm explicacoes referente como obter a chave apikey da api de videos
    @Override
    public void showMessageItemClicked(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContextHomeView() {
        return getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        homePresenter.onItemMenuClicked(item.getItemId());
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

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MOVIE, movie);

        FragmentDetail fragmentDetail = new FragmentDetail();
        fragmentDetail.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragmentDetail);
        fragmentTransaction.commit();

    }
}
