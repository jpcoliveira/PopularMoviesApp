package com.example.android.popularmoviesapp.domain.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.popularmoviesapp.interfaces.interactors.DetailInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.model.ReviewModel;
import com.example.android.popularmoviesapp.model.TrailerModel;
import com.example.android.popularmoviesapp.domain.util.Constants;
import com.example.android.popularmoviesapp.domain.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by joliveira on 5/3/17.
 */

public class MovieDetailAsyncTask extends AsyncTask<MovieModel, Void, MovieModel> {

    Context context;
    DetailInteractor.OnFinishedListener listener;

    public MovieDetailAsyncTask(Context context, DetailInteractor.OnFinishedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected MovieModel doInBackground(MovieModel... movieModels) {
        MovieModel movie = null;

        if (Util.isOnline(context)) {
            movie = getMovieDetail(movieModels[0]);
        }

        return movie;
    }

    @Override
    protected void onPostExecute(MovieModel movieModel) {
        super.onPostExecute(movieModel);
        listener.onFinished(movieModel);
    }

    private MovieModel getMovieDetail(MovieModel movie) {
        List<ReviewModel> reviews = null;
        List<TrailerModel> trailers = null;
        URL urlReviews = null;
        URL urlTrailers = null;
        String strJsonReviews, strJsonTrailers = null;

        try {
            String id = movie.getIdMovie();

            urlReviews = new URL(buildUrl(Constants.REVIEWS, id));
            urlTrailers = new URL(buildUrl(Constants.TRAILER, id));

            strJsonReviews = Util.callServiceByURL(urlReviews, Constants.METHOD_GET);
            strJsonTrailers = Util.callServiceByURL(urlTrailers, Constants.METHOD_GET);

            if (!strJsonReviews.isEmpty()) {
                reviews = strToJsonReview(strJsonReviews);
                movie.setReviews(reviews);
            }

            if (!strJsonTrailers.isEmpty()) {
                trailers = strToJsonTrailer(strJsonTrailers);
                movie.setTrailers(trailers);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return movie;
    }

    private String buildUrl(String filter, String id) {
        Uri builder;

        String base = Constants.URL_BASE_MOVIES;
        HashMap<String, String> parameters = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();
        paths.add(Constants.URL_PATH_3_MOVIES);
        paths.add(Constants.URL_PATH_MOVIE_MOVIES);

        if (filter.equals(Constants.TRAILER))
            paths.add(Constants.URL_PATH_TRAILERS.replace("{id}", id));
        else
            paths.add(Constants.URL_PATH_REVIEWS.replace("{id}", id));

        parameters.put(Constants.API_KEY, Constants.API_KEY_VALUE);

        builder = Util.buildUri(base, parameters, paths);

        return builder.toString();
    }

    private List<ReviewModel> strToJsonReview(String strJson) {
        ReviewModel review;
        List<ReviewModel> reviews = new ArrayList<>();

        try {
            JSONObject jsonObjectReviews = new JSONObject(strJson);
            JSONArray arrayReviews = jsonObjectReviews.getJSONArray("results");

            if (arrayReviews != null) {

                int size = arrayReviews.length();

                for (int i = 0; i < size; i++) {

                    review = new ReviewModel();

                    JSONObject item = arrayReviews.getJSONObject(i);

                    if (item != null) {
                        review.setId(item.getString("id"));
                        review.setAuthor(item.getString("author"));
                        review.setContent(item.getString("content"));
                        review.setUrl(item.getString("url"));
                        reviews.add(review);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    private List<TrailerModel> strToJsonTrailer(String strJson) {

        TrailerModel trailer;
        List<TrailerModel> trailers = new ArrayList<>();

        try {
            JSONObject jsonObjectTrailer = new JSONObject(strJson);
            JSONArray arrayTrailers = jsonObjectTrailer.getJSONArray("results");

            if (arrayTrailers != null) {

                for (int i = 0; i < arrayTrailers.length(); i++) {

                    trailer = new TrailerModel();

                    JSONObject item = arrayTrailers.getJSONObject(i);

                    if (item != null) {
                        trailer.setId(item.getString("id"));
                        trailer.setKey(item.getString("key"));
                        trailer.setName(item.getString("name"));
                        trailer.setSite(item.getString("site"));
                        trailers.add(trailer);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }
}
