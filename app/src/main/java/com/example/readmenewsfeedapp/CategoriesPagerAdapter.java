package com.example.readmenewsfeedapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.readmenewsfeedapp.fragment.GeneralPagerFragment;

public class CategoriesPagerAdapter extends FragmentPagerAdapter {

    // Categories in Pager Adapter
    private static final String FIRST_CATEGORY = "Sport";
    private static final String SECOND_CATEGORY = "Music";
    private static final String THIRD_CATEGORY = "Business";
    private static final String FOURTH_CATEGORY = "World news";
    private static final String FIFTH_CATEGORY = "Film";
    public static final String SIXTH_CATEGORY = "Read Later";

    // Number of Categories
    private int mNumberOfCategories = 6;


    public CategoriesPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("PAGER POSITION", String.valueOf(position));
        switch (position) {
            case 0:
                return GeneralPagerFragment.getInstance(FIRST_CATEGORY,position);
            case 1:
                return GeneralPagerFragment.getInstance(SECOND_CATEGORY, position);
            case 2:
                return GeneralPagerFragment.getInstance(THIRD_CATEGORY,position);
            case 3:
                return GeneralPagerFragment.getInstance(FOURTH_CATEGORY,position);
            case 4:
                return GeneralPagerFragment.getInstance(FIFTH_CATEGORY,position);
            case 5:
                return GeneralPagerFragment.getInstance(SIXTH_CATEGORY, position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumberOfCategories;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return FIRST_CATEGORY;
            case 1:
                return SECOND_CATEGORY;
            case 2:
                return THIRD_CATEGORY;
            case 3:
                return FOURTH_CATEGORY;
            case 4:
                return FIFTH_CATEGORY;
            case 5:
                return SIXTH_CATEGORY;
            default:
                return null;
        }
    }
}
