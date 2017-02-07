package com.example.android.newsreader;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private String url = "https://newsapi.org/v1/articles?source=sky-news&sortBy=top&apiKey=956bea4d81884c1a94dbc84f344c9e43";
    private TextView mEmptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateView = (TextView) findViewById(R.id.empty_view);

        getLoaderManager().initLoader(0, null, this);
    }

    public static ArrayList<News> extractFeaturesFromNews(String requestJson) {
        ArrayList<News> arrayList = new ArrayList<>();
        String jsonResponse = HttpUtil.makeHttpRequest(requestJson);

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultObject = jsonArray.getJSONObject(i);
                String title = resultObject.getString("title");
                String description = resultObject.getString("description");
                String imageUrl = resultObject.getString("urlToImage");
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());

                arrayList.add(new News(title, description, bitmap));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(MainActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        /**
         * Hide the loading progress bar from the screen, as loading should now be finished.
         */
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        /**
         * Set NewsAdapter and attach ArrayList received as a parameter.
         */
        NewsAdapter itemsAdapter = new NewsAdapter(MainActivity.this, (ArrayList) newses);
        ListView listView = (ListView) findViewById(R.id.list_item);

        // Set empty state of the activity
        listView.setEmptyView(mEmptyStateView);

        listView.setAdapter(itemsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

}