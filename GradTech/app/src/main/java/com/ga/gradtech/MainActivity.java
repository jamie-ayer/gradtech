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

    private List<Object> cards;

    private static final String TWITTER_KEY = ApiKeys.TWITTER_KEY;
    private static final String TWITTER_SECRET = ApiKeys.TWITTER_SECRET;

    @Bind(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setLayoutManager();

        initializeAdapter();

        bindDataToAdapter();
    }

    private ArrayList<Object> getSampleArrayList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new Card(getLayoutInflater().inflate(R.layout.card_view_layout, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));
        items.add(new Card2(getLayoutInflater().inflate(R.layout.card_view_layout_2, null)));

        return items;
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(cards);
        recyclerView.setAdapter(adapter);
    }

    private void bindDataToAdapter() {
        recyclerView.setAdapter(new RVAdapter(getSampleArrayList()));
    }

    private void setLayoutManager() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
    }

}
