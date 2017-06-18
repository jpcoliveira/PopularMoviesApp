package com.example.android.popularmoviesapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
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
 * Created by joliveira on 5/21/17.
 */

public class DetailFragment extends Fragment implements
        DetailView, TrailerAdapterOnClickListener, View.OnClickListener {

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
    private LinearLayout containerDetail;
    private LinearLayout containerReviews;
    private LinearLayout containerTrailers;
    private MovieModel mMovie;
    private Context mContext;
    private PackageManager packageManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        trailerAdapter = new TrailerAdapter(this);
        trailerAdapter.setAdapter(new ArrayList<TrailerModel>());

        reviewAdapter = new ReviewAdapter();
        reviewAdapter.setAdapter(new ArrayList<ReviewModel>());

        interactor = new DetailInteractorImpl(mContext);
        presenter = new DetailPresenterImpl(this, interactor);

        containerDetail = (LinearLayout) view.findViewById(R.id.linear_detail_container);

        containerReviews = (LinearLayout) view.findViewById(R.id.container_reviews);
        containerTrailers = (LinearLayout) view.findViewById(R.id.container_trailers);
        progress = (ProgressBar) view.findViewById(R.id.progress_detail);
        textViewErrorNoData = (TextView) view.findViewById(R.id.error_no_data);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(mContext);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewTrailer = (RecyclerView) view.findViewById(R.id.recycle_trailers);
        recyclerViewTrailer.setLayoutManager(layoutManager1);
        recyclerViewTrailer.setAdapter(trailerAdapter);

        recyclerViewReviews = (RecyclerView) view.findViewById(R.id.recycle_reviews);
        recyclerViewReviews.setLayoutManager(layoutManager2);
        recyclerViewReviews.setAdapter(reviewAdapter);

        btnFavorite = (ImageView) view.findViewById(R.id.btn_favorite);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvYear = (TextView) view.findViewById(R.id.tv_year);
        tvAverage = (TextView) view.findViewById(R.id.tv_average);
        tvOverview = (TextView) view.findViewById(R.id.tv_overview);
        image = (ImageView) view.findViewById(R.id.img_movie_detail);

        btnFavorite.setOnClickListener(this);

        MovieModel movie = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            movie = arguments.getParcelable(Constants.MOVIE);
            mMovie = movie;
            presenter.findDetailMovie(movie);
        } else {
            setItemDetail(null);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_favorite) {
            presenter.saveMovie(mMovie);
        }
    }

    @Override
    public void onClickListener(TrailerModel trailer) {
        Uri uri = presenter.buildURLTrailer(trailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        containerDetail.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        containerDetail.setVisibility(View.VISIBLE);
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
                Picasso.with(mContext).load(movieModel.getThumbnail()).into(image);

            if (movieModel.isFavorite()) {
                btnFavorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            } else {
                btnFavorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSilver));
            }
        } else {
            textViewErrorNoData.setVisibility(View.VISIBLE);
            containerDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContextView() {
        return mContext;
    }

    @Override
    public void getPackageManagerApp(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @Override
    public void setColorFavorite(boolean isFavorite) {
        if (isFavorite) {
            btnFavorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            btnFavorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorSilver));
        }
    }
}
