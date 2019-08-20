package com.example.readmenewsfeedapp.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readmenewsfeedapp.CategoriesRecyclerAdapter;
import com.example.readmenewsfeedapp.DetailActivity;
import com.example.readmenewsfeedapp.R;
import com.example.readmenewsfeedapp.data.DatabaseLoader;
import com.example.readmenewsfeedapp.data.NewsContract.NewsEntry;
import com.example.readmenewsfeedapp.model.Article;
import com.example.readmenewsfeedapp.network.FetchArticles;

import java.util.ArrayList;

public class GeneralPagerFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Article>>,
        CategoriesRecyclerAdapter.OnCategoryItemClick, CategoriesRecyclerAdapter.OnSaveButtonItemClick {

    private static final String LOADER_TYPE = "loaderType";

    // bundle key receiving query param
    private static String CATEGORY = "CATEGORY";

    // Detail Activity Bundle key
    public static String DETAILBUNDLE = "detailData";

    // Recycler view adapter variable
    private CategoriesRecyclerAdapter categoriesRecyclerAdapter;
    private RecyclerView mCategoryRecyclerView;

    // Actual category
    public static final String QUERY_STRING = "queryString";

    // Loader ID
    private int mLoaderId = 0;

    // Loader ID constant
    private static String LOADERID = "LOADERID";

    //Query Parameter
    private String mQuery;
    private int page = 1;

    private Boolean isScrolling = false;
    private int currentItems;
    private int totalItems;
    private int scrollItems;

    private ArrayList mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // receive bundle extras
        mQuery = this.getArguments().getString(CATEGORY);
        mLoaderId = this.getArguments().getInt(LOADERID);

        View v = inflater.inflate(R.layout.fragment_categories_recycler, container, false);

        return v;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("ON RESUME", String.valueOf(mLoaderId));
//        getMoreFromApi();
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LinearLayoutManager lm = new LinearLayoutManager(getContext());



        mCategoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        mCategoryRecyclerView.setLayoutManager(lm);
        // find recycler view and set layout manager
        mCategoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = lm.getChildCount();
                totalItems = lm.getItemCount();
                scrollItems = lm.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollItems == totalItems)) {
                    isScrolling = false;

                    mCategoryRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            Log.d("# de items", String.valueOf(page));
                            getMoreFromApi();
                        }
                    });
                }
            }
        });

        if (mLoaderId != 5) {
            // Create a bundle for the async task loader
            loadFromApi();
        } else {
            loadReadLater();
        }
    }

    private void loadFromApi() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_STRING, mQuery);
        LoaderManager.getInstance(getActivity()).initLoader(mLoaderId, queryBundle, this);
    }

    private void loadReadLater() {
        //TODO restart loader was needed to call to make refresh data
        Bundle queryBundle = new Bundle();
        queryBundle.putInt(LOADER_TYPE, mLoaderId);
        LoaderManager.getInstance(getActivity()).initLoader(mLoaderId, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.v("LOADED", String.valueOf(mLoaderId));
        if (args != null) {
            if (id != 5) {
                String queryString = args.getString(QUERY_STRING);
                return new FetchArticles(getContext(), queryString, page);
            } else {
                return new DatabaseLoader(getContext());
            }

        } else {
            return null;
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Article>> loader,
                               ArrayList<Article> data) {

        // bind data with Recycler View Adapter
        bindData(data);
    }

    private void bindData(ArrayList<Article> data) {

//        mData.addAll(data);

        if (categoriesRecyclerAdapter == null) {
            categoriesRecyclerAdapter = new CategoriesRecyclerAdapter(data, this, this, mLoaderId);
            mCategoryRecyclerView.setAdapter(categoriesRecyclerAdapter);
        } else {
            if (mCategoryRecyclerView.getAdapter() == null) {
                mCategoryRecyclerView.setAdapter(categoriesRecyclerAdapter);
            }
            categoriesRecyclerAdapter.addItems(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Article>> loader) {

    }

    @Override
    public void onItemClick(Article news) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DETAILBUNDLE, news);

        startActivity(intent);
    }

    public static GeneralPagerFragment getInstance(String query, int LoaderId) {
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, query);
        bundle.putInt(LOADERID, LoaderId);
        GeneralPagerFragment fragment = new GeneralPagerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onButtonClick(Article article, int position) {
        if (mLoaderId != 5) {
            // ContentValues object
            ContentValues values = new ContentValues();
            values.put(NewsEntry.COLUMN_ARTICLE_BODY, article.getBody());
            values.put(NewsEntry.COLUMN_ARTICLE_THUMBNAIL, article.getThumbnail());
            values.put(NewsEntry.COLUMN_ARTICLE_HEADLINE, article.getHeadline());
            values.put(NewsEntry.COLUMN_ARTICLE_WEB_URL, article.getWebUrl());
            values.put(NewsEntry.COLUMN_ARTICLE_SECTION, article.getSectionName());

            // Insert new article into provider, returning content URI for new article
            Uri newUri = getContext().
                    getContentResolver().insert(NewsEntry.CONTENT_URI, values);

            // Toast message showing insertion result
            if (newUri == null) {
                Toast.makeText(getContext(), getString(R.string.editor_insert_article_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.editor_insert_article_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            String[] slectionArgs = {article.getHeadline()};
            int deletedArticle = getContext().getContentResolver().delete(NewsEntry.CONTENT_URI,
                    NewsEntry.COLUMN_ARTICLE_HEADLINE + "=?", slectionArgs);

            if (deletedArticle < 0) {
                Toast.makeText(getContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();
                //TODO call delete function, and update view calling notify data set changed
                categoriesRecyclerAdapter.deleteItem(position);
                categoriesRecyclerAdapter.notifyDataSetChanged();
            }
        }


    }

    private void getMoreFromApi() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_STRING, mQuery);
        LoaderManager.getInstance(getActivity()).restartLoader(mLoaderId, queryBundle, this);
    }

    public void reload() {
        loadReadLater();
    }
}
