package com.example.android.popularmoviesapp.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.util.Constants;
import com.example.android.popularmoviesapp.util.SizeImages;
import com.example.android.popularmoviesapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joliveira on 5/1/17.
 */

public class MoviesAsyncTask extends AsyncTask<String, Void, List<MovieModel>> {

    HomeInteractor.OnFinishedListener listener;
    Context mContext;

    public MoviesAsyncTask(Context context, HomeInteractor.OnFinishedListener listener) {
        this.listener = listener;
        this.mContext = context;
    }

    @Override
    protected List<MovieModel> doInBackground(String... strings) {

        List<MovieModel> listMovies = null;

        if (Util.isOnline(mContext)) {
            listMovies = findMovies(strings[0]);
        }

        return listMovies;
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);
        listener.onFinished(movieModels);
    }

    private List<MovieModel> findMovies(String filter) {
        Uri builder;
        String base = Constants.URL_BASE_MOVIES;
        HashMap<String, String> parameters = new HashMap<>();
        ArrayList<String> paths = new ArrayList<>();
        paths.add(Constants.URL_PATH_3_MOVIES);
        paths.add(Constants.URL_PATH_MOVIE_MOVIES);
        parameters.put(Constants.API_KEY, Constants.API_KEY_VALUE);

        if (filter.equals("popular")) {
            paths.add(Constants.URL_PATH_POPULAR_MOVIES);
        } else {
            paths.add(Constants.URL_PATH_TOP_RATED_MOVIES);
        }

        builder = Util.buildUri(base, parameters, paths);

        URL url = null;
        String strJson;
        List<MovieModel> movies = null;
        try {
            url = new URL(builder.toString());
            strJson = Util.getJsonResponseRestfulService(url);
            movies = strToJson(strJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private List<MovieModel> strToJson(String strJson) {

        List<MovieModel> movies = new ArrayList<>();
        MovieModel movie;

        ArrayList<String> paths = new ArrayList<>();
        paths.add(Constants.URL_PATH_T_IMAGE);
        paths.add(Constants.URL_PATH_P_IMAGE);
        paths.add(SizeImages.w185.name());

        try {
            JSONObject jsonObjectMovies = new JSONObject(strJson);
            JSONArray arrayMovies = jsonObjectMovies.getJSONArray("results");

            for (int i = 0; i < arrayMovies.length(); i++) {

                movie = new MovieModel();

                JSONObject item = arrayMovies.getJSONObject(i);

                String urlbaseThumbnail = Util.buildUri(Constants.URL_BASE_IMG, null, paths).toString();

                movie.setTitle(item.getString("original_title"));
                movie.setThumbnail(urlbaseThumbnail + item.getString("poster_path"));
                movie.setSynopsis(item.getString("overview"));
                movie.setRating(item.getDouble("vote_average"));
                movie.setDate(item.getString("release_date"));

                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
