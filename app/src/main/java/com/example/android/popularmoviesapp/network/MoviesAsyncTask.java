package com.example.android.popularmoviesapp.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmoviesapp.interfaces.interactors.HomeInteractor;
import com.example.android.popularmoviesapp.model.MovieModel;
import com.example.android.popularmoviesapp.util.Constants;
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

    public MoviesAsyncTask(HomeInteractor.OnFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<MovieModel> doInBackground(String... strings) {
        List<MovieModel> listMovies = findMovies("popular");
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
        String[] paths = new String[0];
        paths[0] = Constants.URL_PATH_3_MOVIES;
        paths[1] = Constants.URL_PATH_MOVIE_MOVIES;
        parameters.put(Constants.API_KEY, Constants.API_KEY_VALUE);

        if (filter.equals("popular")) {
            paths[2] = Constants.URL_PATH_POPULAR_MOVIES;
        } else {
            paths[2] = Constants.URL_PATH_TOP_RATED_MOVIES;
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
        return null;
    }
}
