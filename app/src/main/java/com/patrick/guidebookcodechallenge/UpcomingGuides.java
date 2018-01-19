package com.patrick.guidebookcodechallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_guides);

        upcomingGuides = new ArrayList<>();

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

                    
                }
            }
        });


    }
}
