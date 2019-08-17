package com.example.readmenewsfeedapp.network;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    // Page number
    private static int mPageNumber = 1;

    // Base URL and parameters for API request
    private static final String GUARDIAN_BASE_URL =
            "https://content.guardianapis.com/search?";
    private static final String FROM_DATE = "from-date";
    private static final String TO_DATE = "to-date";
    private static final String FIELDS = "show-fields";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "page-size";
    private static final String ASK = "q";
    private static final String KEY = "api-key";

    // Takes Search term and return JSON String
    public String getArticleInfo(String newsCategory) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufReader = null;
        String articleJSONString = null;

        try {
            // URI build and query issue
            Uri builtURI = Uri.parse(GUARDIAN_BASE_URL).buildUpon()
                    .appendQueryParameter(FROM_DATE, "2019-07-14")
                    .appendQueryParameter(TO_DATE, "2019-07-21")
                    .appendQueryParameter(FIELDS, "all")
                    .appendQueryParameter(PAGE, String.valueOf(mPageNumber))
                    .appendQueryParameter(PAGE_SIZE, "30")
                    .appendQueryParameter(ASK, newsCategory)
                    .appendQueryParameter(KEY, "302d4a96-9bc7-49d3-8f61-7541b4bd9d17")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpsURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // InputStream
            InputStream inputStream = urlConnection.getInputStream();

            // Buffered reader from input stream
            bufReader = new BufferedReader(new InputStreamReader(inputStream));

            // StringBuilder to hold incoming response
            StringBuilder stringBuilder = new StringBuilder();

            // Read input line by line into string
            String line;
            while ((line = bufReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            if (stringBuilder.length() == 0) {
                // Stream empty
                return null;
            }

            articleJSONString = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close connection
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return articleJSONString;
    }
}
