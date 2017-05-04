package com.example.android.popularmoviesapp.interfaces.presenters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.model.MovieModel;

/**
 * Created by joliveira on 5/2/17.
 */

public interface BaseFragmentPresenter {
    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void onCreateView(@Nullable Bundle savedInstanceState, Bundle arguments);

    void onCreate(MovieModel movie);
}
