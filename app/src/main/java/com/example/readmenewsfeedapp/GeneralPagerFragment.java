package com.example.readmenewsfeedapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class GeneralPagerFragment extends Fragment {

    //Query Parameter
    String mQuery;

    // Current Tab in View Pager
    String mCurrentTab;

    // GeneralPagerFragment constructor receives query param for each category
    public GeneralPagerFragment(String query){
        mQuery = query;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.categories_fragment, container, false);

        TextView textView = v.findViewById(R.id.textView);
        textView.setText(mQuery);

        return v;
    }
}
