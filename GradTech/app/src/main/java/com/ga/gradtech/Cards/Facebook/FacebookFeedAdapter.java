package com.ga.gradtech.Cards.Facebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ga.gradtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsiu on 4/20/16.
 */
public class FacebookFeedAdapter extends ArrayAdapter<FacebookFeedObject.FbData> {
    private static final String TAG = "FacebookFeedAdapter";
    List<FacebookFeedObject.FbData> data;

    public FacebookFeedAdapter(Context context, ArrayList<FacebookFeedObject.FbData> objects){
        super(context, -1, objects);
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fbFeedItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_facebook_feed_layout, parent, false);
        TextView feedItemTextView = (TextView)fbFeedItemView.findViewById(R.id.card_facebook_feed_adapter_textView);

        FacebookFeedObject.FbData feedItem = data.get(position);
        String feedMessage = feedItem.getMessage();

        Log.d(TAG, "getView: =======>>>>>>>" + feedMessage);

        feedItemTextView.setText(feedMessage);

        return fbFeedItemView;
    }
}
