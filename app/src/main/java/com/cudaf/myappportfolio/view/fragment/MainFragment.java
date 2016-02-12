package com.cudaf.myappportfolio.view.fragment;

import com.cudaf.myappportfolio.R;
import com.cudaf.myappportfolio.model.Movie;
import com.cudaf.myappportfolio.util.SortOption;
import com.cudaf.myappportfolio.view.activity.DetailActivity;
import com.cudaf.myappportfolio.view.adapter.MovieArrayAdapter;
import com.cudaf.myappportfolio.view.async.MovieDBTask;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends Fragment implements OnTaskCompleted, OnSortCallback, AdapterView.OnItemClickListener {

    private MovieArrayAdapter mMovieArrayAdapter;
    @Bind(R.id.gridview) GridView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mMovieArrayAdapter = new MovieArrayAdapter(getActivity(), R.layout.item_movie);
        ButterKnife.bind(this.getActivity());
        mGridView.setAdapter(mMovieArrayAdapter);
        mGridView.setOnItemClickListener(this);
        MovieDBTask movieDBTask = new MovieDBTask(this);
        movieDBTask.execute();
        return rootView;
    }

    @Override
    public void onTaskCompleted(List<Movie> movies) {
        mMovieArrayAdapter.clear();
        mMovieArrayAdapter.addAll(movies);
        mMovieArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSort(SortOption option) {
        new MovieDBTask(this).execute(option.getValue());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.MOVIE, ((MovieArrayAdapter.MovieViewHolder) view.getTag()).movie);
        startActivity(detailIntent);
    }
}
