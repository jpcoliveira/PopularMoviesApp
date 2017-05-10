package com.example.android.popularmoviesapp.interactors;

import android.content.Context;

import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.domain.network.MovieDetailAsyncTask;

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

        if (!movie.isFavorite()) {
            MovieDetailAsyncTask detailAsyncTask = new MovieDetailAsyncTask(context, listener);
            detailAsyncTask.execute(movie);
        } else {
            listener.onFinished(getFavoriteMovie(movie));
        }
    }

    private MovieModel getFavoriteMovie(MovieModel movie) {
        return MovieModel.find(MovieModel.class, "idMovie = ?", movie.getIdMovie()).get(0);
    }

    @Override
    public boolean save(MovieModel movie) {
        boolean retorno = false;
        try {
            if (!existsMovie(movie)) {
                movie.save();
            } else {
                movie.delete();
            }
            retorno = true;
        } catch (Exception ex) {
            retorno = false;
        }
        return retorno;
    }

    private boolean existsMovie(MovieModel movie) {
        List<MovieModel> list = new ArrayList<>();
        list = MovieModel.findWithQuery(MovieModel.class, "select * from MovieModel where idMovie = '" + movie.getIdMovie() + "'");
        return list.size() > 0;
    }
}
