package com.example.readmenewsfeedapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.readmenewsfeedapp.model.Article;
import com.example.readmenewsfeedapp.network.FetchArticles;

import java.util.ArrayList;

//import static com.example.readmenewsfeedapp.CategoriesPagerAdapter.SIXTH_CATEGORY;


public class GeneralPagerFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Article>>,
        CategoriesRecyclerAdapter.OnCategoryItemClick {

    // bundle key receiving query param
    private static String CATEGORY = "CATEGORY";

    // Detail Activity Bundle key
    private static String DETAILBUNDLE = "detailData";

    // Recycler view adapter variable
    private CategoriesRecyclerAdapter categoriesRecyclerAdapter;
    private RecyclerView mCategoryRecyclerView;

    // Actual category
    private static final String QUERY_STRING = "queryString";

    // Loader ID
    private int loaderId=0;

    // Loader ID constant
    private static String LOADERID = "LOADERID";

    //Query Parameter
    private String mQuery;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // receive bundle extras
        mQuery = this.getArguments().getString(CATEGORY);
        this.loaderId=this.getArguments().getInt(LOADERID);

        View v = inflater.inflate(R.layout.fragment_categories_recycler, container, false);

        // find recycler view and set layout manager
        mCategoryRecyclerView = v.findViewById(R.id.category_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(getContext());
        mCategoryRecyclerView.setLayoutManager(lm);

//        if (mQuery != SIXTH_CATEGORY) {
//            // Create a bundle for the async task loader
//            Bundle queryBundle = new Bundle();
//            queryBundle.putString(QUERY_STRING, mQuery);
//            LoaderManager.getInstance(getActivity()).restartLoader(0, queryBundle, this);
//        } else {
//            // Loader from database
//        }

        // Create a bundle for the async task loader
        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_STRING, mQuery);
        LoaderManager.getInstance(getActivity()).restartLoader(loaderId, queryBundle, this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, @Nullable Bundle args) {

        if (args != null) {
            String queryString = args.getString(QUERY_STRING);
            return new FetchArticles(getContext(), queryString);
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

        categoriesRecyclerAdapter = new CategoriesRecyclerAdapter(data, this);
        mCategoryRecyclerView.setAdapter(categoriesRecyclerAdapter);
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
        bundle.putInt(LOADERID,LoaderId);
        GeneralPagerFragment fragment = new GeneralPagerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}
