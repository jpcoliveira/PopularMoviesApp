package com.example.android.popularmoviesapp.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.util.Constants;

/**
 * Created by joliveira on 4/28/17.
 */

public class FragmentDetail extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MovieModel movie = getArguments().getParcelable(Constants.MOVIE);
//        Toast.makeText(getActivity(), movie.toString(), Toast.LENGTH_LONG).show();

        return view;
    }
}
