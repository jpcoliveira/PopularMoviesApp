package com.example.android.popularmoviesapp.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.android.popularmoviesapp.fragments.DetailFragment;
import com.example.android.popularmoviesapp.fragments.MainFragment;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.util.Constants;
import com.example.android.popularmoviesapp.interactors.MainInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.views.DetailView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.presenters.MainPresenterImpl;

/**
 * Created by joliveira on 4/28/17.
 */
public class MainActivity extends ActionBarActivity implements MainFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DETAIL_FRAG";
    private static final String MAINFRAGMENT_TAG = "MAIN_FRAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the largescreen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                DetailFragment fragment = new DetailFragment();

                DetailView detailView;
                detailView = fragment;
                detailView.getPackageManagerApp(getPackageManager());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, fragment, DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onItemSelected(MovieModel movie) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(Constants.MOVIE, movie);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            DetailView detailView;
            detailView = fragment;
            detailView.getPackageManagerApp(getPackageManager());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {

            Bundle args = new Bundle();
            args.putParcelable(Constants.MOVIE, movie);

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtras(args);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
