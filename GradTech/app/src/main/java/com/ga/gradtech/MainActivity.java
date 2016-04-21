package com.ga.gradtech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import butterknife.Bind;
import butterknife.ButterKnife;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    public static CallbackManager callbackManager;



    private static final String TWITTER_KEY = ApiKeys.TWITTER_KEY;
    private static final String TWITTER_SECRET = ApiKeys.TWITTER_SECRET;

    @Bind(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ==>>> Before callbackManager");
        callbackManager = CallbackManager.Factory.create();
        Log.d(TAG, "onCreate: =====>>> After Callback Manager");

        ButterKnife.bind(this);

        setLayoutManager();

        initializeAdapter();

    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(5, this);
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManager() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
