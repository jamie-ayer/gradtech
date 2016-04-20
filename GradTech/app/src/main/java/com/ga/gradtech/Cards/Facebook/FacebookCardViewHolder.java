package com.ga.gradtech.Cards.Facebook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.ga.gradtech.R;

/**
 * Created by samsiu on 4/19/16.
 */
public class FacebookCardViewHolder extends RecyclerView.ViewHolder {
    public LoginButton mFbLoginButton;
    public TextView mFbFeedTextView;
    public Button mFbShareButton;
    public Button mFbGetFeedButton;

    public FacebookCardViewHolder(View itemView){
        super(itemView);
        this.mFbLoginButton = (LoginButton)itemView.findViewById(R.id.fb_login_button);
        this.mFbFeedTextView = (TextView)itemView.findViewById(R.id.card_fb_textView);
        this.mFbShareButton = (Button)itemView.findViewById(R.id.card_fb_share_button);
        this.mFbGetFeedButton = (Button)itemView.findViewById(R.id.card_fb_get_feed_button);

    }
}


