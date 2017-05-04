package com.example.android.popularmoviesapp.activities;


import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.adapters.TrailerAdapter;
import com.example.android.popularmoviesapp.interactors.DetailInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.listeners.TrailerAdapterOnClickListener;
import com.example.android.popularmoviesapp.interfaces.presenters.DetailPresenter;
import com.example.android.popularmoviesapp.interfaces.views.DetailView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.model.TrailerModel;
import com.example.android.popularmoviesapp.presenters.DetailPresenterImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joliveira on 4/28/17.
 */

public class FragmentDetail extends Fragment implements DetailView, TrailerAdapterOnClickListener {

    private DetailPresenter presenter;
    private DetailInteractorImpl interactor;
    private ProgressBar progress;
    private TextView textViewErrorNoData;
    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReviews;
    private TrailerAdapter adapter;

    private Button btnFavorite;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvTime;
    private TextView tvAverage;
    private TextView tvOverview;
    private ImageView image;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        adapter = new TrailerAdapter(this);
        adapter.setAdapter(new ArrayList<TrailerModel>());

        interactor = new DetailInteractorImpl(getActivity());
        presenter = new DetailPresenterImpl(this, interactor);
        progress = (ProgressBar) view.findViewById(R.id.progress_detail);
        textViewErrorNoData = (TextView) view.findViewById(R.id.error_no_data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


//        recyclerViewTrailer = (RecyclerView) view.findViewById(R.id.recycle_trailers);
//        recyclerViewTrailer.setLayoutManager(layoutManager);
//        recyclerViewTrailer.setAdapter(adapter);

        recyclerViewReviews = (RecyclerView) view.findViewById(R.id.recycle_reviews/*R.id.recycle_reviews*/);
        recyclerViewReviews.setLayoutManager(layoutManager);
        recyclerViewReviews.setAdapter(adapter);

//        recyclerViewTrailer.setHasFixedSize(true);
        recyclerViewReviews.setHasFixedSize(true);

        btnFavorite = (Button) view.findViewById(R.id.btn_favorite);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvYear = (TextView) view.findViewById(R.id.tv_year);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvAverage = (TextView) view.findViewById(R.id.tv_average);
        tvOverview = (TextView) view.findViewById(R.id.tv_overview);
        image = (ImageView) view.findViewById(R.id.img_movie_detail);


        presenter.onCreateView(savedInstanceState, getArguments());


        return view;
    }

    @Override
    public void showProgress() {
//        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        progress.setVisibility(View.GONE);
    }

    @Override
    public void setItemDetail(MovieModel movieModel) {
        if (movieModel != null) {
            Toast.makeText(getActivity(), movieModel.toString(), Toast.LENGTH_LONG).show();
            adapter.setAdapter(movieModel.getTrailers());
            tvTitle.setText(movieModel.getTitle());
            tvYear.setText(movieModel.getDate());
            tvTime.setText("50 min");
            tvAverage.setText(Double.toString(movieModel.getRating()));
            tvOverview.setText(movieModel.getSynopsis());
            Picasso.with(getActivity()).load(movieModel.getThumbnail()).into(image);
        }
    }

    @Override
    public void onClickListener(TrailerModel trailer) {
        Toast.makeText(getActivity(), trailer.toString(), Toast.LENGTH_LONG).show();
    }
}
