package com.ga.gradtech.Cards.SoundCloud;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by JamieAyer on 4/14/16.
 */
public class CustomIntentService extends IntentService {

    private static String TAG = "Custom Intent Service";

    public CustomIntentService() {
        super("HelloIntentService");
        Log.i(TAG, "CustomService Constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent: started");

        SCService scService = SoundCloud.getService();
        scService.getRecentTracks(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                //loadTracks(tracks);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error: " + error);
            }
        });


        try {
            Thread.sleep(10000);
            Log.i(TAG, "Custom Intent Service ended");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Custom Intent Service destroyed");
    }
}