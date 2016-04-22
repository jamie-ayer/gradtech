package com.ga.gradtech.Cards.Facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ga.gradtech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsiu on 4/21/16.
 */
public class FacebookPageAdapter extends ArrayAdapter<FacebookPageObject.FbPageData> {
    private static final String TAG = "FacebookPageAdapter";
    List<FacebookPageObject.FbPageData> data;

    public FacebookPageAdapter(Context context, ArrayList<FacebookPageObject.FbPageData> objects){
        super(context, -1, objects);
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fbFeedItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_facebook_feed_layout, parent, false);
        TextView feedItemTextView = (TextView)fbFeedItemView.findViewById(R.id.card_facebook_feed_adapter_textView);

        FacebookPageObject.FbPageData feedItem = data.get(position);
        String feedMessage = feedItem.getMessage();

        feedItemTextView.setText(feedMessage);

        return fbFeedItemView;
    }
}
