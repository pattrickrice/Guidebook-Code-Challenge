package com.patrick.guidebookcodechallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Main class for the challenge. Makes a GET request to a given url and displays the returned data
 * into a recylerview.
 */
public class UpcomingGuides extends AppCompatActivity {

    // create logtag for printing out from activity
    final String LOGTAG = this.getClass().getSimpleName();

    // create OkHttpClient object
    private OkHttpClient client = new OkHttpClient();
    private JSONObject jsonObject = null;
    private ArrayList<GuideDataModel> upcomingGuides;
    private UpcomingGuideAdapter adapter;
    private RelativeLayout errorLayout;
    private TextView errorTV;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_guides);

        errorLayout = findViewById(R.id.error_layout);
        errorTV = findViewById(R.id.error_tv);
        mHandler = new Handler(Looper.getMainLooper());

        upcomingGuides = new ArrayList<>();

        adapter = new UpcomingGuideAdapter(this, upcomingGuides);
        ListView upcomingGuidesList = findViewById((R.id.guide_listview));
        upcomingGuidesList.setAdapter(adapter);

        getUpcomingGuides();


        // Reload button appears if the content never loaded.
        // Allows the user to attempt to reload.
        final Button button = findViewById(R.id.reload_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUpcomingGuides();
            }
        });
    }

    /**
     * Fetches the guides from the provided URL using OkHttp
     * */
    void getUpcomingGuides() {
        if (isNetworkConnected()) {
            // MILESTONE 3: Display your objects in a RecyclerView
            // request data using OkHttp

            // provided API call
            String url = "https://guidebook.com/service/v2/upcomingGuides/";

            // create request object for OkHttp
            final Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Response was not successful: " + response);
                    } else {
                        String jsondata = response.body().string();
                        Log.v(LOGTAG,
                                "Milestone 1: print out Retrieve and print out the data received from the url");

                        Log.v(LOGTAG, jsondata);

                        try {
                            jsonObject = new JSONObject(jsondata);
                            JSONArray jsonArray = jsonObject.getJSONArray(Keys.KEY_DATA);

                            int lengthOfJSONArray = jsonArray.length();
                            if (lengthOfJSONArray > 0) {
                                for (int i = 0; i < lengthOfJSONArray; i++) {

                                    GuideDataModel model = new GuideDataModel();
                                    String startDate = " ";
                                    String endDate = " ";
                                    String guideURL = " ";
                                    String name = " ";
                                    String icon = " ";
                                    String city = "";
                                    String state = "";

                                    // parse JSON object
                                    JSONObject guideItem = jsonArray.getJSONObject(i);

                                    // check if item is present
                                    if (guideItem.has(Keys.KEY_START_DATE)) {
                                        startDate = guideItem.getString(Keys.KEY_START_DATE);
                                    }
                                    if (guideItem.has(Keys.KEY_END_DATE)) {
                                        endDate = guideItem.getString(Keys.KEY_END_DATE);
                                    }
                                    if (guideItem.has(Keys.KEY_URL)) {
                                        guideURL = guideItem.getString(Keys.KEY_URL);
                                    }
                                    if (guideItem.has(Keys.KEY_NAME)) {
                                        name = guideItem.getString(Keys.KEY_NAME);
                                    }
                                    if (guideItem.has(Keys.KEY_ICON)) {
                                        icon = guideItem.getString(Keys.KEY_ICON);
                                    }

                                    // getting info from venue object
                                    JSONObject venueItem = guideItem.getJSONObject(Keys.KEY_VENUE);

                                    // URL did not return item in venue object at time of writing this.
                                    if (venueItem.has(Keys.KEY_CITY) && venueItem.has(Keys.KEY_STATE)) {
                                        city = guideItem.getString(Keys.KEY_CITY);
                                        state = guideItem.getString(Keys.KEY_STATE);
                                    }

                                    // set to data structure
                                    model.setStartDate(startDate);
                                    model.setEndDate(endDate);
                                    model.setUrl(guideURL);
                                    model.setName(name);
                                    model.setIcon(icon);
                                    model.setCity(city);
                                    model.setState(state);

                                    // add to the list
                                    // MILESTONE 2: Parse the data retrieved from the server into a list of Java objects
                                    upcomingGuides.add(model);

                                    // If we didn't load the guides, show the error layout.
                                    if (upcomingGuides.size() == 0) {
                                        errorLayout.setVisibility(View.VISIBLE);
                                    } else if (errorLayout.getVisibility() == View.VISIBLE) {
                                        errorLayout.setVisibility(View.GONE);
                                    }

                                    // update the adapter.
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // MILESTONE 3: Display your objects in a RecyclerView
                                            // request data using OkHttp
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            // no internet connection
                errorLayout.setVisibility(View.VISIBLE);
                errorTV.setText(R.string.no_internet_connection);
        }
    }

    /**
     * Check that we have internet connection
     */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }
}
