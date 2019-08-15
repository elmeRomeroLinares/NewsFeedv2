package com.example.readmenewsfeedapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.readmenewsfeedapp.data.NewsDbHelper;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // View pager
    private ViewPager mNewsCategoriesVP;

    // Tab Layout for View Pager
    private TabLayout mTabNewsCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find pager and tab by ID
        mNewsCategoriesVP = findViewById(R.id.pager);
        mTabNewsCategories = findViewById(R.id.tab_layout);

        // News pager adapter object
        CategoriesPagerAdapter categoriesPA =
                new CategoriesPagerAdapter(getSupportFragmentManager());

        // Setup Pager with Tab
        mNewsCategoriesVP.setAdapter(categoriesPA);
        mTabNewsCategories.setupWithViewPager(mNewsCategoriesVP);

    }
}
