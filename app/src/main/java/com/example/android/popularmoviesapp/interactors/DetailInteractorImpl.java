package com.example.android.popularmoviesapp.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.domain.adapters.TrailerAdapter;
import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.network.MovieDetailAsyncTask;
import com.example.android.popularmoviesapp.model.ReviewModel;
import com.example.android.popularmoviesapp.model.TrailerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joliveira on 5/3/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    private Context context;

    public DetailInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void findDetailMovie(OnFinishedListener listener, MovieModel movie) {

        MovieModel _movie = getFavoriteMovie(movie);

        if (_movie == null) {
            MovieDetailAsyncTask detailAsyncTask = new MovieDetailAsyncTask(context, listener);
            detailAsyncTask.execute(movie);
        } else {
            listener.onFinished(_movie);
        }
    }

    private MovieModel getFavoriteMovie(MovieModel movie) {
        List<MovieModel> _movie = MovieModel.find(MovieModel.class, "id_movie = ?", movie.getIdMovie());
        return _movie != null && _movie.size() > 0 ? _movie.get(0) : null;
    }

    @Override
    public MovieModel save(MovieModel movie) {
        try {
            MovieModel _movie = getFavoriteMovie(movie);

            if (_movie == null) {
                movie.setId(null);
                movie.setFavorite(true);

                List<ReviewModel> reviews = movie.getReviews();
                List<TrailerModel> trailers = movie.getTrailers();

                for (ReviewModel review : reviews) {
                    review.setId(null);
                    review.save();
                }

                for (TrailerModel trailer : trailers) {
                    trailer.setId(null);
                    trailer.save();
                }
                movie.setTrailers(TrailerModel.find(TrailerModel.class, ""));
                movie.setReviews(ReviewModel.find(ReviewModel.class, ""));
                movie.save();

            } else {

                List<ReviewModel> reviews = _movie.getReviews();
                List<TrailerModel> trailers = _movie.getTrailers();

                for (ReviewModel review : reviews) {
                    review.delete();
                }

                for (TrailerModel trailer : trailers) {
                    trailer.delete();
                }

                _movie.delete();

                movie.setFavorite(false);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return movie;
    }
}