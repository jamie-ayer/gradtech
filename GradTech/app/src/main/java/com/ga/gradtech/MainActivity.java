package com.ga.gradtech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.ga.gradtech.Cards.Calander.CalendarCard;
import com.ga.gradtech.Cards.Facebook.FacebookCard;
import com.ga.gradtech.Cards.NotePad.NotePadCard;
import com.ga.gradtech.Cards.SoundCloud.Config;
import com.ga.gradtech.Cards.SoundCloud.SoundCloudCard;
import com.ga.gradtech.Cards.SoundCloud.Track;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    public static CallbackManager callbackManager;

    private List<Object> cards;

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

        bindDataToAdapter();

    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        //Facebook Card
        items.add(new FacebookCard());
        //Twitter Card
        items.add(new Card2());
        items.add(new SoundCloudCard());
        items.add(new CalendarCard());
        items.add(new Card2());
        items.add(new Card2());
        items.add(new Card2());
        items.add(new NotePadCard());


        return items;
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(cards, this);
        recyclerView.setAdapter(adapter);
    }

    private void bindDataToAdapter() {
        recyclerView.setAdapter(new RVAdapter(getSampleArrayList(), this));
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
