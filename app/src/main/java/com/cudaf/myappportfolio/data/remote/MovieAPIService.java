package com.cudaf.myappportfolio.data.remote;

import com.cudaf.myappportfolio.model.Movie;
import com.cudaf.myappportfolio.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cudaf on 13/02/2016.
 */
public interface MovieAPIService {

    final String SORT_PARAM = "sort_by";
    final String API_KEY_PARAM = "api_key";
    final String MOVIES_BASE_URL = "http://api.themoviedb.org";

    @GET("/3/discover/movie")
    Call<Response> getMovies(@Query(SORT_PARAM)String sort, @Query(API_KEY_PARAM)String apiKey);
}
