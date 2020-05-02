package com.teachableapps.popularmoviesstage1.utilities;

import android.util.Log;

import com.teachableapps.popularmoviesstage1.model.MoviesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static ArrayList<MoviesClass> parseMoviesJson(String json) {
        try {

            MoviesClass movie;
            JSONObject json_object = new JSONObject(json); //(json.replace("\\",""));

            JSONArray resultsArray = new JSONArray(json_object.optString("results","[\"\"]"));

            ArrayList<MoviesClass> movieitems = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                String thisitem = resultsArray.optString(i, "");
                JSONObject movieJson = new JSONObject(thisitem);

                movie = new MoviesClass(
                        movieJson.optString("original_title","Not Available"),
                        movieJson.optString("release_date","Not Available"),
                        movieJson.optString("vote_average","Not Available"),
                        movieJson.optString("popularity","Not Available"),
                        movieJson.optString("overview","Not Available"),
                        movieJson.optString("poster_path","Not Available"),
                        movieJson.optString("backdrop_path","Not Available")
                );

                movieitems.add(movie);
            }
//            Log.d(TAG,"Number of Movies " + resultsArray.length());
            return movieitems;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
