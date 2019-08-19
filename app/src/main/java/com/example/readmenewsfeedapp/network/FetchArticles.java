package com.example.readmenewsfeedapp.network;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.readmenewsfeedapp.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchArticles extends AsyncTaskLoader<ArrayList<Article>> {

    String mQueryString;
    int page;

    public FetchArticles(@NonNull Context context, String queryString, int page) {
        super(context);

        this.page = page;
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // call loadInBackground
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Article> loadInBackground() {

        ArrayList<Article> articlesArrayList = new ArrayList<>();

        String s = new NetworkUtils().getArticleInfo(mQueryString, page);

        // handle incorrect JSON input or update
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray itemsArray = response.getJSONArray("results");

            int i = 0;
            String sectionName = null;
            String webUrl = null;

            String headline = null;
            String thumbnail = null;
            String body = null;

            while (i < itemsArray.length()) {
                JSONObject article = itemsArray.getJSONObject(i);
                JSONObject fields = article.getJSONObject("fields");

                try {
                    sectionName = article.getString("sectionName");
                    webUrl = article.getString("webUrl");
                    headline = fields.getString("headline");
                    thumbnail = fields.getString("thumbnail");
                    body = fields.getString("body");

                    Article generalArticles =
                            new Article(sectionName, webUrl, headline, thumbnail, body);
                    articlesArrayList.add(generalArticles);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articlesArrayList;
    }
}
