package com.example.android.popularmoviesapp.domain.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joliveira on 4/28/17.
 */

public class Util {

    /**
     * verify conection with internet
     **/
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * build Uri.toString() -> URL
     **/
    public static Uri buildUri(String base, HashMap<String, String> parameter, ArrayList<String> paths) {
        Uri.Builder builder = Uri.parse(base)
                .buildUpon();

        if (paths != null && paths.size() > 0) {
            for (String path : paths) {
                builder.appendEncodedPath(path);
            }
        }

        if (parameter != null && parameter.size() > 0) {
            for (Map.Entry<String, String> item : parameter.entrySet()) {
                builder.appendQueryParameter(item.getKey(), item.getValue());
            }
        }
        return builder.build();
    }

    /**
     * get string json response of call service
     **/
    public static String callServiceByURL(URL url, String method) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonStr = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);//GET, POST, DELETE ...
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                jsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                jsonStr = null;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("callServiceByUrl", "Error ", e);
            jsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("callServiceByUrl", "Error closing stream", e);
                }
            }
        }

        return jsonStr;
    }
}
