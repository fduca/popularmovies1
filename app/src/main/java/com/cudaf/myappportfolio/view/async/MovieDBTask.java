package com.cudaf.myappportfolio.view.async;

import com.cudaf.myappportfolio.model.Movie;
import com.cudaf.myappportfolio.util.SortOption;
import com.cudaf.myappportfolio.view.fragment.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDBTask extends AsyncTask<Object, Movie, List<Movie>> {

    private final String LOG_TAG = MovieDBTask.class.getSimpleName();

    String sorting = SortOption.MOST_POPULAR.getValue();
    final String MOVIES_LIST_URL = "http://api.themoviedb.org/3/discover/movie?";
    final String MOVIE_BY_ID_URL = "http://api.themoviedb.org/3/movie/{1}?";
    final String API_KEY = "";
    final String SORT_PARAM = "sort_by";
    final String API_KEY_PARAM = "api_key";

    private OnTaskCompleted mOnTaskCompleted;

    public MovieDBTask(OnTaskCompleted onTaskCompleted) {
        this.mOnTaskCompleted = onTaskCompleted;
    }

    @Override
    protected List<Movie> doInBackground(Object... params) {
        if (params.length > 0) {
            sorting = (String) params[0];
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesList = null;
        try {
            Uri builtURI = Uri.parse(MOVIES_LIST_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, sorting)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
            URL url = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            moviesList = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getMoviesFromJson(moviesList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mOnTaskCompleted.onTaskCompleted(movies);
        }
    }

    private List<Movie> getMoviesFromJson(String moviesList) throws JSONException {
        final String RESULT = "results";
        final String ID = "id";
        final String ORIGINAL_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        List<Movie> movies = new ArrayList<>();
        JSONObject moviesListJson = new JSONObject(moviesList);
        JSONArray moviesArray = moviesListJson.getJSONArray(RESULT);
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);
            Movie movie = new Movie();
            movie.setId(movieJson.getLong(ID));
            movie.setOriginalTitle(movieJson.getString(ORIGINAL_TITLE));
            movie.setOverview(movieJson.getString(OVERVIEW));
            movie.setVoteAverage(movieJson.getLong(VOTE_AVERAGE));
            movie.setReleaseDate(movieJson.getString(RELEASE_DATE));
            movie.setPosterPath(movieJson.getString(POSTER_PATH));
            movies.add(movie);
        }
        return movies;
    }
}
