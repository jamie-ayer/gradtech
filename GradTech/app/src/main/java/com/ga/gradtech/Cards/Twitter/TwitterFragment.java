package com.ga.gradtech.Cards.Twitter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ga.gradtech.R;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.File;

/**
 * Created by JacobDexter-Milling on 4/20/16.
 */
public class TwitterFragment extends Fragment {

    TwitterLoginButton loginButton;
    Button tweet;
    File myImageFile = new File(String.valueOf(R.drawable.tigger));
    final Uri myImageUri = Uri.fromFile(myImageFile);

    ListView twitter_listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twitter_fragment_layout, container, false);

        setRetainInstance(true);

        tweet = (Button) v.findViewById(R.id.tweet);
        tweet.setVisibility(View.INVISIBLE);
        twitter_listview = (ListView) v.findViewById(R.id.twitter_list_view);
        twitter_listview.setVisibility(View.INVISIBLE);

        loginButton = (TwitterLoginButton) v.findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Log.i("TwitterFragment", "Signed in");
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show(); // getContext might not be right

                loginButton.setVisibility(View.INVISIBLE);
                tweet.setVisibility(View.VISIBLE);
                twitter_listview.setVisibility(View.VISIBLE);

                // check if you're truly logged in or not.
                // hide login button and show list view + tweet button + grab tweets from interwebs
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }


        });

        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetComposer.Builder builder = new TweetComposer.Builder(getContext()) // might be getActivity
                        .text("")
                        .image(myImageUri);
                builder.show();
            }
        });


        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("fabric")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext()) // getContext might be wrong
                .setTimeline(userTimeline)
                .build();
        twitter_listview.setAdapter(adapter);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        if (loginButton != null){
            Log.i("TwitterFragment", "getting twitter data ");
            loginButton.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
