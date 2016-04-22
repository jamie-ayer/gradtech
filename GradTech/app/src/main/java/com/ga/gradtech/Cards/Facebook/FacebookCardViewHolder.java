package com.ga.gradtech.Cards.Facebook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;
import com.ga.gradtech.R;

/**
 * Created by samsiu on 4/19/16.
 */
public class FacebookCardViewHolder extends RecyclerView.ViewHolder {
    public TextView mFbPageHeaderTextView;
    public LoginButton mFbLoginButton;
    public ShareButton mFbShareWidgetButton;
    public SendButton mFbSendButton;
    public Button mFbShareButton;
    public Button mFbGetFeedButton;
    public Button mFbGetPageFeedButton;
    public ListView mFbFeedListView;
    public EditText mFbShareTitleEditText;
    public EditText mFbShareDescriptionEditText;
    public EditText mFbShareUrlEditText;

    public FacebookCardViewHolder(View itemView){
        super(itemView);
        this.mFbPageHeaderTextView = (TextView)itemView.findViewById(R.id.card_fb_page_header_textView);
        this.mFbLoginButton = (LoginButton)itemView.findViewById(R.id.card_fb_login_button);
        this.mFbShareButton = (Button)itemView.findViewById(R.id.card_fb_share_button);
        this.mFbShareWidgetButton = (ShareButton)itemView.findViewById(R.id.card_fb_shareWidget_button);
        this.mFbSendButton = (SendButton)itemView.findViewById(R.id.card_fb_send_button);
        this.mFbGetFeedButton = (Button)itemView.findViewById(R.id.card_fb_get_feed_button);
        this.mFbGetPageFeedButton = (Button)itemView.findViewById(R.id.card_fb_get_page_feed_button);
        this.mFbFeedListView = (ListView)itemView.findViewById(R.id.card_fb_feed_listView);
        this.mFbShareTitleEditText = (EditText)itemView.findViewById(R.id.card_facebook_share_title_editText);
        this.mFbShareDescriptionEditText = (EditText)itemView.findViewById(R.id.card_facebook_share_description_editText);
        this.mFbShareUrlEditText = (EditText)itemView.findViewById(R.id.card_facebook_share_url_editText);
    }
}


