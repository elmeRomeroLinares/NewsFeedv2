package com.example.readmenewsfeedapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.readmenewsfeedapp.DetailActivity;
import com.example.readmenewsfeedapp.R;

import static com.example.readmenewsfeedapp.DetailActivity.FRAGMENTDATA;

public class DetailFragment extends Fragment {

    public DetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String[] data = this.getArguments().getStringArray(FRAGMENTDATA);
        final String mThumbnail = data[0];
        final String mBodyText = data[1];
        final String mUrl = data[2];

        View v = inflater.inflate(R.layout.fragment_datail_activity, container, false);

        ImageView imageView = v.findViewById(R.id.imageView);
        Glide.with(getContext()).load(mThumbnail).fitCenter().into(imageView);

        // Displays HTML content
        WebView webView = v.findViewById(R.id.textView);
        webView.loadDataWithBaseURL(null, mBodyText, "text/html", "utf-8", null);

        Button openBrowser = v.findViewById(R.id.button2);
        openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                startActivity(browserIntent);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
