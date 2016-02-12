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

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    public static final String MOVIE = "movie";

    Movie mMovie;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.movie_title)
    TextView mOriginalTitle;
    @Bind(R.id.movie_synopsis)
    TextView mMovieSynopsis;
    @Bind(R.id.movie_rating)
    TextView mMovieRating;
    @Bind(R.id.movie_year)
    TextView mMovieYear;
    @Bind(R.id.movie_image)
    ImageView mMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Movie movie = intent.getExtras().getParcelable(DetailActivity.MOVIE);
        if (movie != null) {
            mMovie = movie;
        }
        mOriginalTitle.setText(mMovie.getOriginalTitle());
        Picasso.with(this)
            .load(mMovie.getFullPosterPath())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(mMovieImage);
        mMovieSynopsis.setText(mMovie.getOverview());
        mMovieRating.setText(String.format(getResources().getString(R.string.movie_rating), mMovie.getVoteAverage()));
        mMovieYear.setText(String.format(getResources().getString(R.string.movie_date), getYear(mMovie.getReleaseDate())));
    }

    private String getYear(String releaseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
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
