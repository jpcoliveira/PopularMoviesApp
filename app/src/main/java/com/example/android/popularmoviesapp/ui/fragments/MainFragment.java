package com.example.android.popularmoviesapp.ui.fragments;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.ui.adapters.MoviesAdapter;
import com.example.android.popularmoviesapp.util.Constants;
import com.example.android.popularmoviesapp.util.Util;
import com.example.android.popularmoviesapp.domain.interactors.MainInteractorImpl;
import com.example.android.popularmoviesapp.ui.listeners.MoviesAdapterOnClickHandler;
import com.example.android.popularmoviesapp.ui.presenters.MainPresenter;
import com.example.android.popularmoviesapp.ui.views.MainView;
import com.example.android.popularmoviesapp.data.model.MovieModel;
import com.example.android.popularmoviesapp.ui.presenters.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/21/17.
 */

public class MainFragment extends Fragment implements MainView, MoviesAdapterOnClickHandler, View.OnClickListener {

    private MainPresenter presenter;
    private MainInteractorImpl interactor;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private MoviesAdapter adapter;
    private TextView textViewErrorNoData;
    private int idMenuActive;
    private Bundle mSavedInstanceState;
    private Context mContext;
    private String titleToolbar;
    private TextView textViewNoInternetConnection;
    private Button btnRetryConnection;

    public interface Callback {
        void onItemSelected(MovieModel movie);
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
        interactor = new MainInteractorImpl(getActivity());
        presenter = new MainPresenterImpl(this, interactor);
        textViewErrorNoData = (TextView) view.findViewById(R.id.tv_msg_no_data);
        textViewNoInternetConnection = (TextView) view.findViewById(R.id.tv_msg_no_internet);
        btnRetryConnection = (Button) view.findViewById(R.id.btn_retry_connection);
        btnRetryConnection.setOnClickListener(this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_movies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        mSavedInstanceState = savedInstanceState;
        presenter.findMovies(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.TITLE_TOOLBAR)) {
                titleToolbar = savedInstanceState.getString(Constants.TITLE_TOOLBAR);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titleToolbar);
            }
        }

        return view;
    }

    public void reloadFavorites() {
        if (idMenuActive == R.id.action_order_favorite) {
            presenter.onItemMenuClicked(idMenuActive);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_retry_connection) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        presenter.onItemMenuClicked(idMenuActive);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void clickItemListener(MovieModel movie) {
        if ((Util.isOnline(getActivity())) || ((!Util.isOnline(getActivity())) && idMenuActive == R.id.action_order_favorite)) {
            ((Callback) getActivity()).onItemSelected(movie);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
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
            textViewErrorNoData.setVisibility(View.GONE);
            textViewNoInternetConnection.setVisibility(View.GONE);
            btnRetryConnection.setVisibility(View.GONE);
            adapter.setMovies(movies);
        } else {
            adapter.setMovies(null);
            textViewErrorNoData.setVisibility(View.VISIBLE);
            textViewNoInternetConnection.setVisibility(View.GONE);
            btnRetryConnection.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getContextHomeView() {
        return mContext;
    }

    @Override
    public void showMessageNoInternet() {
        adapter.setMovies(null);
        textViewNoInternetConnection.setVisibility(View.VISIBLE);
        btnRetryConnection.setVisibility(View.VISIBLE);
        textViewErrorNoData.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<MovieModel> movies = (ArrayList<MovieModel>) adapter.getMovies();
        outState.putParcelableArrayList(Constants.MOVIES, movies);
        outState.putString(Constants.TITLE_TOOLBAR, ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());
        outState.putInt(Constants.MENU, idMenuActive);
        mSavedInstanceState = outState;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.MENU))
                idMenuActive = savedInstanceState.getInt(Constants.MENU);
            if (savedInstanceState.containsKey(Constants.TITLE_TOOLBAR))
                titleToolbar = savedInstanceState.getString(Constants.TITLE_TOOLBAR);
        }
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        idMenuActive = id;
        presenter.onItemMenuClicked(id);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(item.getTitle().toString());
        return true;
    }
}
