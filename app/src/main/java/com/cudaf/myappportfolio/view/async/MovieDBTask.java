package com.cudaf.myappportfolio.view.async;

import com.cudaf.myappportfolio.data.remote.MovieAPIService;
import com.cudaf.myappportfolio.data.remote.MovieAPIServiceClient;
import com.cudaf.myappportfolio.model.Movie;
import com.cudaf.myappportfolio.model.Response;
import com.cudaf.myappportfolio.util.SortOption;
import com.cudaf.myappportfolio.view.fragment.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MovieDBTask extends AsyncTask<Object, Movie, List<Movie>> {

    private final String LOG_TAG = MovieDBTask.class.getSimpleName();

    String sorting = SortOption.MOST_POPULAR.getValue();
    final String API_KEY = "";

    private OnTaskCompleted mOnTaskCompleted;

    public MovieDBTask(OnTaskCompleted onTaskCompleted) {
        this.mOnTaskCompleted = onTaskCompleted;
    }

    @Override
    protected List<Movie> doInBackground(Object... params) {
        if (params.length > 0) {
            sorting = (String) params[0];
        }
        MovieAPIService movieAPIService = MovieAPIServiceClient.createService(MovieAPIService.class);
        Call<Response> call = movieAPIService.getMovies(sorting, API_KEY);
        try {
            return call.execute().body().getMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mOnTaskCompleted.onTaskCompleted(movies);
        }
    }
}
