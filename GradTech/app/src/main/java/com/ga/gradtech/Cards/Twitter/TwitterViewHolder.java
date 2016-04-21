package com.ga.gradtech.Cards.Twitter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.ga.gradtech.R;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by JacobDexter-Milling on 4/21/16.
 */
public class TwitterViewHolder extends RecyclerView.ViewHolder {

    public FrameLayout frameLayout;
    public TwitterLoginButton twitter_login_button;
    public Button tweet;
    public ListView list_view;
    public Fragment fragment;

    public TwitterViewHolder(View itemView) {
        super(itemView);
        this.frameLayout = (FrameLayout) itemView.findViewById(R.id.twitter_card_parent_layout);
        this.twitter_login_button = (TwitterLoginButton) itemView.findViewById(R.id.twitter_login_button);
        this.tweet = (Button) itemView.findViewById(R.id.tweet);
        this.list_view = (ListView) itemView.findViewById(R.id.twitter_list_view);

    }

}
