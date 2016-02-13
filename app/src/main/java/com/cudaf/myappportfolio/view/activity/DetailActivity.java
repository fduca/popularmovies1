package com.cudaf.myappportfolio.view.activity;

import com.cudaf.myappportfolio.R;
import com.cudaf.myappportfolio.model.Movie;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {


    public static final String MOVIE = "movie";

    Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(DetailActivity.MOVIE);
        if (movie != null) {
            mMovie = movie;
        }
        ((TextView) findViewById(R.id.movie_title)).setText(mMovie.getOriginalTitle());
        Picasso.with(this)
            .load(mMovie.getFullPosterPath()).into((ImageView) findViewById(R.id.movie_image));

        ((TextView) findViewById(R.id.movie_synopsis)).setText(mMovie.getOverview());
        ((TextView) findViewById(R.id.movie_rating)).setText(String.format(getResources().getString(R.string.movie_rating), mMovie.getVoteAverage()));
        ((TextView) findViewById(R.id.movie_year)).setText(String.format(getResources().getString(R.string.movie_date), getYear(mMovie.getReleaseDate())));
    }

    private String getYear(String releaseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse;
        try {
            parse = sdf.parse(releaseDate);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            return "" + c.get(Calendar.YEAR);
        } catch (ParseException e) {
            return "";
        }
    }

}