package com.example.android.popularmoviesapp.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.interactors.HomeInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.presenters.HomePresenter;
import com.example.android.popularmoviesapp.interfaces.views.HomeView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.presenters.HomeImplPresenter;

import java.util.List;

/**
 * Created by joliveira on 4/28/17.
 */

public class FragmentHome extends Fragment implements HomeView, AdapterView.OnItemClickListener {

    private HomePresenter homePresenter;
    private HomeInteractorImpl homeInteractor;
    private ListView listView;
    ProgressBar progress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeInteractor = new HomeInteractorImpl();
        homePresenter = new HomeImplPresenter(this, homeInteractor);
        listView = (ListView) view.findViewById(R.id.listteste);
        listView.setOnItemClickListener(this);
        progress = (ProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovieItem(List<MovieModel> movies) {
        listView.setAdapter(new ArrayAdapter<MovieModel>(getActivity(), android.R.layout.simple_list_item_1, movies));
    }

    @Override
    public void showMessageItemClicked(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        homePresenter.onIntemClicked(i);
    }
}
