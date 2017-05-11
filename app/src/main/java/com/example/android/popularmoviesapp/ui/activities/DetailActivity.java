package com.example.android.popularmoviesapp.ui.activities;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.domain.adapters.ReviewAdapter;
import com.example.android.popularmoviesapp.domain.adapters.TrailerAdapter;
import com.example.android.popularmoviesapp.domain.util.Constants;
import com.example.android.popularmoviesapp.interactors.DetailInteractorImpl;
import com.example.android.popularmoviesapp.interfaces.listeners.ReviewAdapterOnClickListener;
import com.example.android.popularmoviesapp.interfaces.listeners.TrailerAdapterOnClickListener;
import com.example.android.popularmoviesapp.interfaces.presenters.DetailPresenter;
import com.example.android.popularmoviesapp.interfaces.views.DetailView;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.model.ReviewModel;
import com.example.android.popularmoviesapp.model.TrailerModel;
import com.example.android.popularmoviesapp.presenters.DetailPresenterImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by joliveira on 4/28/17.
 */

public class DetailActivity extends AppCompatActivity implements
        DetailView, TrailerAdapterOnClickListener, ReviewAdapterOnClickListener, View.OnClickListener {

    private DetailPresenter presenter;
    private DetailInteractorImpl interactor;
    private ProgressBar progress;
    private TextView textViewErrorNoData;
    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReviews;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private ImageView btnFavorite;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvAverage;
    private TextView tvOverview;
    private ImageView image;
    private LinearLayout container;
    private LinearLayout containerReviews;
    private LinearLayout containerTrailers;
    private MovieModel mMovie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        trailerAdapter = new TrailerAdapter(this);
        trailerAdapter.setAdapter(new ArrayList<TrailerModel>());

        reviewAdapter = new ReviewAdapter(this);
        reviewAdapter.setAdapter(new ArrayList<ReviewModel>());

        interactor = new DetailInteractorImpl(this);
        presenter = new DetailPresenterImpl(this, interactor);

        container = (LinearLayout) findViewById(R.id.detail_container);
        containerReviews = (LinearLayout) findViewById(R.id.container_reviews);
        containerTrailers = (LinearLayout) findViewById(R.id.container_trailers);
        progress = (ProgressBar) findViewById(R.id.progress_detail);
        textViewErrorNoData = (TextView) findViewById(R.id.error_no_data);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewTrailer = (RecyclerView) findViewById(R.id.recycle_trailers);
        recyclerViewTrailer.setLayoutManager(layoutManager1);
        recyclerViewTrailer.setAdapter(trailerAdapter);

        recyclerViewReviews = (RecyclerView) findViewById(R.id.recycle_reviews);
        recyclerViewReviews.setLayoutManager(layoutManager2);
        recyclerViewReviews.setAdapter(reviewAdapter);

        btnFavorite = (ImageView) findViewById(R.id.btn_favorite);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvAverage = (TextView) findViewById(R.id.tv_average);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        image = (ImageView) findViewById(R.id.img_movie_detail);

        btnFavorite.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        MovieModel movie = bundle.getParcelable(Constants.MOVIE);
        mMovie = movie;

        presenter.onCreate(movie);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItemDetail(MovieModel movieModel) {
        if (movieModel != null) {

            mMovie = movieModel;
            textViewErrorNoData.setVisibility(View.GONE);

            if (movieModel.getTrailers() != null && movieModel.getTrailers().size() > 0) {
                containerTrailers.setVisibility(View.VISIBLE);
                trailerAdapter.setAdapter(movieModel.getTrailers());
            } else {
                containerTrailers.setVisibility(View.GONE);
            }

            if (movieModel.getReviews() != null && movieModel.getReviews().size() > 0) {
                containerReviews.setVisibility(View.VISIBLE);
                reviewAdapter.setAdapter(movieModel.getReviews());
            } else {
                containerReviews.setVisibility(View.GONE);
            }

            if (!movieModel.getTitle().isEmpty())
                tvTitle.setText(movieModel.getTitle());

            if (!movieModel.getDateRelease().isEmpty()) {
                tvYear.setText(presenter.formatDate(movieModel.getDateRelease()));
            }

            if (!movieModel.getRating().isEmpty())
                tvAverage.setText(movieModel.getRating() + getString(R.string.max_average));

            if (!movieModel.getSynopsis().isEmpty())
                tvOverview.setText(movieModel.getSynopsis());

            if (!movieModel.getThumbnail().isEmpty())
                Picasso.with(this).load(movieModel.getThumbnail()).into(image);

            if (movieModel.isFavorite()) {
                btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent));
            } else {
                btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.colorSilver));
            }
        } else {
            textViewErrorNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickListener(TrailerModel trailer) {
        Uri uri = presenter.buildURLTrailer(trailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    public void onClickListener(ReviewModel review) {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        MovieModel _movie;

        if (id == R.id.btn_favorite) {
            _movie = presenter.saveMovie(mMovie);

            if (_movie != null) {
                presenter.onCreate(_movie);
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContextView() {
        return this;
    }
}
