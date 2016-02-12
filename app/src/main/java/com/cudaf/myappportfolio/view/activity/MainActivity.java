package com.cudaf.myappportfolio.view.activity;


import com.cudaf.myappportfolio.R;
import com.cudaf.myappportfolio.util.SortOption;
import com.cudaf.myappportfolio.view.fragment.MainFragment;
import com.cudaf.myappportfolio.view.fragment.OnSortCallback;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    OnSortCallback mOnSortCallback;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getFragmentManager();
        mOnSortCallback = (MainFragment) fragmentManager.findFragmentById(R.id.main_fragment);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_popular:
                mOnSortCallback.onSort(SortOption.MOST_POPULAR);
                break;
            case R.id.sort_rating:
                mOnSortCallback.onSort(SortOption.HIGHEST_RATE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
