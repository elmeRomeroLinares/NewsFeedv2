package com.example.readmenewsfeedapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.readmenewsfeedapp.fragment.DetailFragment;
import com.example.readmenewsfeedapp.model.Article;

import com.example.readmenewsfeedapp.data.NewsContract.NewsEntry;

public class DetailActivity extends AppCompatActivity {

    private static String DETAILBUNDLE = "detailData";

    public static String FRAGMENTDATA = "fragmentData";

    // Article data
    Article mdata;

    // constructor
    public DetailActivity() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get information from intent extras
        mdata = getIntent().getParcelableExtra(DETAILBUNDLE);

        // set bundle to pass information to fragment in detail activity
        Bundle bundleInfo = new Bundle();
        String[] fragmentData = {mdata.getThumbnail(), mdata.getBody(), mdata.getWebUrl()};
        bundleInfo.putStringArray(FRAGMENTDATA, fragmentData);

        // fragment placement on UI
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundleInfo);
        fragmentTransaction.add(R.id.fragment_frame, detailFragment);
        fragmentTransaction.commit();

        TextView articleHeadLine = findViewById(R.id.article_header);
        articleHeadLine.setText(mdata.getHeadline());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_article_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.save_action_button:
                saveArticle();                
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveArticle() {

        // ContentValues object
        ContentValues values = new ContentValues();
        values.put(NewsEntry.COLUMN_ARTICLE_BODY, mdata.getBody());
        values.put(NewsEntry.COLUMN_ARTICLE_THUMBNAIL, mdata.getThumbnail());
        values.put(NewsEntry.COLUMN_ARTICLE_HEADLINE, mdata.getHeadline());
        //values.put(NewsEntry.COLUMN_ARTICLE_WEB_URL, mdata.getWebUrl());
        values.put(NewsEntry.COLUMN_ARTICLE_SECTION, mdata.getSectionName());

        // Insert new article into provider, returning content URI for new article
        Uri newUri = getContentResolver().insert(NewsEntry.CONTENT_URI, values);

        // Toast message showing insertion result
        if (newUri == null) {
            Toast.makeText(this, getString(R.string.editor_insert_article_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_insert_article_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
