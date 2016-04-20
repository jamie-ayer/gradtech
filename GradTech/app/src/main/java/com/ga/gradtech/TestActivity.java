package com.ga.gradtech;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ga.gradtech.Cards.Twitter.TwitterSignInFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_card_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TwitterSignInFragment fragment = new TwitterSignInFragment();
        fragmentTransaction.add(R.id.twitter_card_layout, fragment);
        fragmentTransaction.commit();

    }
}
