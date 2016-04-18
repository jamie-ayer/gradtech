package com.ga.gradtech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    private List<Card> cards;
    private RecyclerView rv;
=======
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = ApiKeys.twitter_key;
    private static final String TWITTER_SECRET = ApiKeys.twitter_secret;


    @Bind(R.id.rv)
    RecyclerView recyclerView;
>>>>>>> 3a82aacb92b47f0fe9f7083e192b17a0bd334987

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        cards = new ArrayList<>();
        cards.add(new Card("Facebook", "1 Hacker Way, Menlo Park, CA 94025", R.drawable.facebook_icon));
        cards.add(new Card("Twitter", "1355 Market St #900, San Francisco, CA 94103", R.drawable.twitter_icon));
        cards.add(new Card("TechCrunch", "410 Townsend San Francisco, CA 94107 United States", R.drawable.techcrunch_icon));
        cards.add(new Card("Trello", "Somewhere", R.drawable.trello_icon));
        cards.add(new Card("GitHub", "Somewhere", R.drawable.github_icon));
        cards.add(new Card("Glassdoor", "Somewhere", R.drawable.glassdoor_icon));
        cards.add(new Card("Linkedin", "Somewhere", R.drawable.linkedin_icon));
        cards.add(new Card("Yelp", "Somewhere", R.drawable.yelp_icon));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(cards);
        rv.setAdapter(adapter);
    }



}
