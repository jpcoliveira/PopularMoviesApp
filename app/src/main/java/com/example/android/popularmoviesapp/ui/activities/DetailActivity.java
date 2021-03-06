package com.example.android.popularmoviesapp.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.popularmoviesapp.ui.fragments.DetailFragment;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.util.Constants;
import com.example.android.popularmoviesapp.ui.views.DetailView;
import com.example.android.popularmoviesapp.data.model.MovieModel;

/**
 * Created by joliveira on 4/28/17.
 */

public class DetailActivity extends AppCompatActivity implements DetailFragment.Callback {

    private DetailView detailView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            MovieModel movie = getIntent().getExtras().getParcelable(Constants.MOVIE);
            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.MOVIE, movie);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            detailView = fragment;
            detailView.getPackageManagerApp(getPackageManager());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(getString(R.string.detail_movie));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                setResult(0);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void reloadFavorites() {

    }
}
