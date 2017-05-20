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

        List<MovieModel> _movies = MovieModel.find(MovieModel.class, "id_movie = ?", movie.getIdMovie());

        if (_movies != null && _movies.size() > 0) {
            List<ReviewModel> reviews = ReviewModel.find(ReviewModel.class, "id_movie = ?", movie.getIdMovie());
            List<TrailerModel> trailers = TrailerModel.find(TrailerModel.class, "id_movie = ?", movie.getIdMovie());
            _movies.get(0).setReviews(reviews);
            _movies.get(0).setTrailers(trailers);
        }

        return _movies != null && _movies.size() > 0 ? _movies.get(0) : null;
    }

    @Override
    public MovieModel save(MovieModel movie) {

        List<ReviewModel> reviews;
        List<TrailerModel> trailers;

        try {
            MovieModel _movie = getFavoriteMovie(movie);

            if (_movie == null) {
                movie.setId(null);
                movie.setFavorite(true);

                reviews = movie.getReviews();
                trailers = movie.getTrailers();

                for (ReviewModel review : reviews) {
                    review.setIdMovie(movie.getIdMovie());
                    review.setId(null);
                    review.save();
                }

                for (TrailerModel trailer : trailers) {
                    trailer.setIdMovie(movie.getIdMovie());
                    trailer.setId(null);
                    trailer.save();
                }

                reviews = ReviewModel.find(ReviewModel.class, "id_movie = ?", movie.getIdMovie());
                trailers = TrailerModel.find(TrailerModel.class, "id_movie = ?", movie.getIdMovie());

                movie.setTrailers(trailers);
                movie.setReviews(reviews);

                movie.save();

            } else {

                reviews = _movie.getReviews();
                trailers = _movie.getTrailers();

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
