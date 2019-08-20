package com.example.readmenewsfeedapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.readmenewsfeedapp.fragment.GeneralPagerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // View pager

    private ViewPager mNewsCategoriesVP;
    // Tab Layout for View Pager

    private TabLayout mTabNewsCategories;

    private CategoriesPagerAdapter mCategoriesPA;

    private FloatingActionButton dayNightFab;

    public static final String DAYNIGHT = "DAY_NIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayNightFab = findViewById(R.id.day_night_action_button);
        dayNightFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performDayNight();
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(MainActivity.this, PanelSearchView.class);
                startActivity(searchIntent);
            }
        });

        // Find pager and tab by ID
        mNewsCategoriesVP = findViewById(R.id.pager);
        //TODO new listener to kmow when tab was selected and perform read later refresh
//        mNewsCategoriesVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 5) {
//                    Fragment page = getSupportFragmentManager().
//                            findFragmentByTag("android:switcher:" + R.id.pager + ":" + position);
//                    ((GeneralPagerFragment) page).reload();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        mTabNewsCategories = findViewById(R.id.tab_layout);

        // News pager adapter object
        mCategoriesPA =
                new CategoriesPagerAdapter(getSupportFragmentManager());

        // Setup Pager with Tab
        mNewsCategoriesVP.setAdapter(mCategoriesPA);
        mTabNewsCategories.setupWithViewPager(mNewsCategoriesVP);

    }

    //TODO decide which mode is active and perform setDefaultNightMode()
    private void performDayNight() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean(DAYNIGHT, true);
                break;
            // Night mode is not active, we're in day time
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean(DAYNIGHT, false);
                break;
            // Night mode is active, we're at night!
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                // We don't know what mode we're in, assume notnight
                break;
        }
        editor.commit();
        recreate();

    }

}
