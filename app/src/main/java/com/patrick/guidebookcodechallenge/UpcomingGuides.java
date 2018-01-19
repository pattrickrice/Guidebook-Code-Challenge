package com.patrick.guidebookcodechallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_guides);

        upcomingGuides = new ArrayList<>();

        adapter = new UpcomingGuideAdapter(this, upcomingGuides);
        ListView upcomingGuidesList = (ListView) findViewById((R.id.guide_listview));
        upcomingGuidesList.setAdapter(adapter);

        if (isNetworkConnected()) {

            // MILESTONE 3: Display your objects in a RecyclerView
            new GetUpComingGuidesTask().execute();
        }
    }

    /**
     * Task fetches data from the url.
     * */
    class GetUpComingGuidesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (upcomingGuides.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to retrieve data",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // provided API call
            String url = "https://guidebook.com/service/v2/upcomingGuides/";

            // create request object for OkHttp
            final Request request = new Request.Builder().url(url).build();

            // request data using OkHttp
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

                        // Handle jsonException

                        try {
                            jsonObject = new JSONObject(jsondata);
                            JSONArray jsonArray = jsonObject.getJSONArray(Keys.KEY_DATA);

                            int lengthOfJSONArray = jsonArray.length();
                            if (lengthOfJSONArray > 0) {
                                for (int i = 0; i < lengthOfJSONArray; i++) {

                                    GuideDataModel model = new GuideDataModel();
                                    String startDate = "";
                                    String endDate = "";
                                    String guideURL = "";
                                    String name = "";
                                    String icon = "";

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
                                    if (guideItem.has(Keys.KEY_ICON)) {
                                        icon = guideItem.getString(Keys.KEY_ICON);
                                    }
                                    if (guideItem.has(Keys.KEY_ICON)) {
                                        icon = guideItem.getString(Keys.KEY_ICON);
                                    }

                                    // set to data structure
                                    model.setStartDate(startDate);
                                    model.setEndDate(endDate);
                                    model.setUrl(guideURL);
                                    model.setName(name);
                                    model.setIcon(icon);

                                    // getting info from venue object
                                    JSONObject venueItem = guideItem.getJSONObject(Keys.KEY_VENUE);

                                    // URL did not return item in venue object at time of writing this.
                                    if (venueItem.has(Keys.KEY_CITY) && venueItem.has(Keys.KEY_STATE)) {
                                        String city = guideItem.getString(Keys.KEY_CITY);
                                        String state = guideItem.getString(Keys.KEY_STATE);

                                        // set to model
                                        model.setCity(city);
                                        model.setState(state);
                                    }

                                    // add to the list
                                    // MILESTONE 2: Parse the data retrieved from the server into a list of Java objects
                                    upcomingGuides.add(model);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            return null;
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
