package com.ga.gradtech;

import android.content.Intent;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import butterknife.Bind;
import butterknife.ButterKnife;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;



import com.ga.gradtech.Cards.Meetup.Fragment.MeetupResultsFragment;
import com.ga.gradtech.Cards.Meetup.OnSuccessfulLoginListener;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;


import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements OnSuccessfulLoginListener{

    private static final String TAG = MainActivity.class.getCanonicalName();
    public static CallbackManager callbackManager;
    FragmentManager meetupFragmentManager;


    private static final String TWITTER_KEY = ApiKeys.TWITTER_KEY;
    private static final String TWITTER_SECRET = ApiKeys.TWITTER_SECRET;

    @Bind(R.id.rv)
    RecyclerView recyclerView;

    //This is for testing only

    FragmentManager fragmentManagerTwitter = getSupportFragmentManager();
    RVAdapter adapter;
    int numberCards = 7;


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

        meetupFragmentManager = getSupportFragmentManager();

    }

    private void initializeAdapter() {
        adapter = new RVAdapter(numberCards, this, this, fragmentManagerTwitter, meetupFragmentManager);
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

        // only call this when request code is for twitter.

        if (adapter != null && adapter.getTwitterFragment() != null) {
            adapter.getTwitterFragment().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSuccessfulLogin(String tokenAccess) {
        MeetupResultsFragment meetupResultsFragment = new MeetupResultsFragment();
        FragmentTransaction fragmentTransaction = meetupFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.meetup_container_id, meetupResultsFragment);
        fragmentTransaction.commitAllowingStateLoss(); //was fragmentTransaction.commit()
        meetupResultsFragment.setAccessToken(tokenAccess);
    }
}

