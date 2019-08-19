package com.example.readmenewsfeedapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.readmenewsfeedapp.model.Article;
import com.example.readmenewsfeedapp.network.FetchArticles;

import java.util.ArrayList;

import static com.example.readmenewsfeedapp.fragment.GeneralPagerFragment.QUERY_STRING;

public class PanelSearchView extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private Toolbar mToolbar;

    private ArrayAdapter mAdapter;

    private ListView mListView;

    private TextView mEmptyView;

    private ArrayList<String> mData = new ArrayList<>();

    private int searchLoaderID = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_search_view);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mListView = findViewById(R.id.list);
        mEmptyView = findViewById(R.id.emptyView);

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mData);

        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(mSearch);
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle queryBundle = new Bundle();
                queryBundle.putString(QUERY_STRING, query);
                LoaderManager.getInstance(PanelSearchView.this).restartLoader(searchLoaderID,
                        queryBundle, PanelSearchView.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("PanelSearchView", "The Loader was created");
        if (args != null) {
            String queryString = args.getString(QUERY_STRING);
            return new FetchArticles(this, queryString, 1);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        for (int i = 0; i < data.size(); i++) {
            mData.add(data.get(i).getHeadline());
        }

        Log.d("PanelSearchView", mData.toString());

        mListView.invalidateViews();
        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Article>> loader) {
        Log.d("PanelSearchView", "onLoaderReset");

    }
}
