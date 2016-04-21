package com.ga.gradtech.Cards.Twitter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ga.gradtech.R;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.File;

/**
 * Created by JacobDexter-Milling on 4/20/16.
 */
public class TwitterFeedFragment extends Fragment{

    Button tweet;
    File myImageFile = new File(String.valueOf(R.drawable.tigger));
    final Uri myImageUri = Uri.fromFile(myImageFile);

    ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twitter_card_feed_inflator, container, false);

        tweet = (Button) v.findViewById(R.id.tweet);
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
        listview.setAdapter(adapter);


        return v;
    }

    @Override
    public void onAttach(Context context) {


        super.onAttach(context);
    }
}
