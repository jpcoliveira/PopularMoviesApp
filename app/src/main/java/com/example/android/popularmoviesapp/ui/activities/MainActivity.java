package com.example.android.popularmoviesapp.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import com.example.android.popularmoviesapp.Fragments.DetailFragment;
import com.example.android.popularmoviesapp.R;

/**
 * Created by joliveira on 4/28/17.
 */
public class MainActivity extends ActionBarActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {

            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_container, new DetailFragment(), "detail")
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }
}
