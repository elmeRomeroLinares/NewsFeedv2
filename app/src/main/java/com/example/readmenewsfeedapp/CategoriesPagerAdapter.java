package com.example.readmenewsfeedapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoriesPagerAdapter extends FragmentPagerAdapter {

    // Categories in Pager Adapter
    private static final String FIRST_CATEGORY = "Sport";
    private static final String SECOND_CATEGORY = "Music";
    private static final String THIRD_CATEGORY = "Business";
    private static final String FOURTH_CATEGORY = "World news";
    private static final String FIFTH_CATEGORY = "Film";
    private static final String SIXTH_CATEGORY = "Read Later";

    // Number of Categories
    private int mNumberOfCategories = 6;

    private static int currentPosition;


    public CategoriesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        currentPosition = position;
        switch (position) {
            case 0:
                return new GeneralPagerFragment(FIRST_CATEGORY);
            case 1:
                return new GeneralPagerFragment(SECOND_CATEGORY);
            case 2:
                return new GeneralPagerFragment(THIRD_CATEGORY);
            case 3:
                return new GeneralPagerFragment(FOURTH_CATEGORY);
            case 4:
                return new GeneralPagerFragment(FIFTH_CATEGORY);
            case 5:
                return new GeneralPagerFragment(SIXTH_CATEGORY);
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
