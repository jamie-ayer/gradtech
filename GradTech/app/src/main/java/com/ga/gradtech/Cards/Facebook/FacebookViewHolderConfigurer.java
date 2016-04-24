package com.ga.gradtech.Cards.Facebook;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ga.gradtech.MainActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Configures Facebook Card
 * Created by samsiu on 4/20/16.
 */
public class FacebookViewHolderConfigurer {
    private static final String TAG = FacebookViewHolderConfigurer.class.getCanonicalName();

    private String mPostId;
    private String mPageId =  "649188628486342";
    private String mPageName=  "Android Page";
    protected FacebookCardViewHolder mVh1;
    protected Activity mMainActivity;

    /**
     * FacebookViewHolderConfigurer Constructor
     * @param vh1
     * @param mainActivity
     */
    public FacebookViewHolderConfigurer(FacebookCardViewHolder vh1, Activity mainActivity) {
        this.mVh1 = vh1;
        this.mMainActivity = mainActivity;
    }

    /**
     * Initialize facebook card
     */
    public void initFacebookCardView(){
        Log.d(TAG, "initFacebookCardView: ==>>> Initializing Facebook");
        setFbLoginButtonVisibility();
        setFbListViewVisibility();
        setFbShareViewVisibility();

        setFbPostShareButtonListener();
        setFbUpdateFeedButtonListener();
        setFbUpdatePageFeedButtonListener();
        setFbPostShareWidgetButtonListener();
        setFbSendMessageButtonListener();
        getFbFeed();
    }

    /**
     * Check if user is logged into facebook
     * @return boolean
     */
    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * Initialize facebook login button
     */
    public void initFbLoginButton(){
        mVh1.mFbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ===>>> Facebook Button Clicked");
                /*
                // Setting permissions on login
                Collection<String> permissions = Arrays.asList("public_profile",
                        "user_friends",
                        "user_status",
                        "user_posts",
                        "user_managed_groups",
                        "publish_actions");
                LoginManager.getInstance().logInWithReadPermissions(mainActivity, permissions);
                */
                // Callback after login attempt
                LoginManager.getInstance().registerCallback(MainActivity.fbCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "onSuccess: ====>>> FBLogin Successful");
                                initFacebookCardView();

                                Toast toast = Toast.makeText(mMainActivity.getApplicationContext(), "Successful FB Login", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onCancel() {
                                Log.d(TAG, "onCancel: ===>>> FBLogin Cancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.d(TAG, "onError: ======>>>> FBlogin Error");
                                exception.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    /**
     * Show login button when not logged in, hide when logged in
     */
    private void setFbLoginButtonVisibility(){
        if(isFacebookLoggedIn()){
            mVh1.mFbLoginButton.setVisibility(View.GONE);
        }else{
            mVh1.mFbLoginButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show feed when logged in, hide when logged out
     */
    private void setFbListViewVisibility(){
        if(isFacebookLoggedIn()){
            mVh1.mFbFeedListView.setVisibility(View.VISIBLE);
        }else{
            mVh1.mFbFeedListView.setVisibility(View.GONE);
        }
    }

    /**
     * Show views when logged in, hide when logged out
     */
    private void setFbShareViewVisibility(){
        if(isFacebookLoggedIn()){
            /*
            // Currently not using feature
            vh1.mFbPageHeaderTextView.setVisibility(View.VISIBLE);
            vh1.mFbShareButton.setVisibility(View.VISIBLE);
            */
            mVh1.mFbShareDescriptionEditText.setVisibility(View.VISIBLE);
            mVh1.mFbShareTitleEditText.setVisibility(View.VISIBLE);
            mVh1.mFbShareUrlEditText.setVisibility(View.VISIBLE);
            mVh1.mFbGetFeedButton.setVisibility(View.VISIBLE);
            mVh1.mFbGetPageFeedButton.setVisibility(View.VISIBLE);
            mVh1.mFbShareWidgetButton.setVisibility(View.VISIBLE);
//            vh1.mFbSendButton.setVisibility(View.VISIBLE);
        }else{
            /*
            // Currently not using feature
            vh1.mFbPageHeaderTextView.setVisibility(View.GONE);
            vh1.mFbShareButton.setVisibility(View.GONE);
            */
            mVh1.mFbShareDescriptionEditText.setVisibility(View.GONE);
            mVh1.mFbShareTitleEditText.setVisibility(View.GONE);
            mVh1.mFbShareUrlEditText.setVisibility(View.GONE);
            mVh1.mFbGetFeedButton.setVisibility(View.GONE);
            mVh1.mFbGetPageFeedButton.setVisibility(View.GONE);
            mVh1.mFbShareWidgetButton.setVisibility(View.GONE);
//            vh1.mFbSendButton.setVisibility(View.GONE);
        }
    }

    /**
     * Gets title EditText input and returns default if empty
     * @param defaultTitle
     * @return String title
     */
    private String getTitleEditText(String defaultTitle){
        String title = mVh1.mFbShareTitleEditText.getText().toString();
        title = title.isEmpty() ? defaultTitle : title;
        return title;
    }

    /**
     * Gets description EditText input and returns default if empty
     * @param defaultDescription
     * @return String description
     */
    private String getDescriptionEditText(String defaultDescription){
        String description = mVh1.mFbShareDescriptionEditText.getText().toString();
        description = description.isEmpty() ? defaultDescription : description;
        return description;
    }

    /**
     * Gets url EditText input and returns default if empty
     * @param defaultUrl
     * @return String url
     */
    private String getUrlEditText(String defaultUrl){
        String url = mVh1.mFbShareUrlEditText.getText().toString();
        url = url.isEmpty() ? defaultUrl : url;
        return url;
    }


    /**
     * Set the Share button listener to send messages from editText
     */
    private void setFbPostShareWidgetButtonListener(){
        mVh1.mFbShareWidgetButton.setEnabled(true);
        mVh1.mFbShareWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getTitleEditText("");
                String description = getDescriptionEditText("");
                String url = getUrlEditText("");
                setupShareDialog(title, description, url);
            }
        });
    }

    /**
     * Creates a share link facebook dialog box and handles callbacks
     * @param title
     * @param description
     * @param url
     */
    private void setupShareDialog(String title, String description, String url){
        ShareDialog shareDialog = new ShareDialog(mMainActivity);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            // Share Link Parameters
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(description)
                    .setContentUrl(Uri.parse(url))
                    .build();
            // Inject content to share dialog
            shareDialog.show(content);
            // Handle Callback
            shareDialog.registerCallback(MainActivity.fbCallbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    getFbFeed();
                    mVh1.mFbShareTitleEditText.setText("");
                    mVh1.mFbShareDescriptionEditText.setText("");
                    mVh1.mFbShareUrlEditText.setText("");
                }
                @Override
                public void onCancel() {
                    Toast.makeText(mMainActivity, "FB Share Canceled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    error.printStackTrace();
                }
            });
        }
    }


    /**
     * Update feed button will populate user's feed messages to app
     */
    private void setFbUpdateFeedButtonListener(){
        mVh1.mFbGetFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVh1.mFbPageHeaderTextView.setText("Facebook Feed");
                getFbFeed();
            }
        });
    }

    /**
     * Populate app with messages from Android Page
     */
    private void setFbUpdatePageFeedButtonListener(){
        mVh1.mFbGetPageFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVh1.mFbPageHeaderTextView.setText(mPageName);
                getFbPageFeed();
            }
        });
    }

    /**
     * Get messages from user's feed
     */
    private void getFbFeed() {
        if(isFacebookLoggedIn()){
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/feed",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Gson gson = new Gson();
                            String fbFeedJson = response.getJSONObject().toString();
                            FacebookFeedObject fbFeed = gson.fromJson(fbFeedJson, FacebookFeedObject.class);

                            /*
                            //
                            Log.d(TAG, "onCompleted: FeedJson " + fbFeedJson);
                            Log.d(TAG, "onCompleted: " + fbFeed.getData().toString());
                            mPostId = fbFeed.getData().get(0).getId();
                            Log.d(TAG, "onCompleted: ID<><><><" + mPostId);
                            */

                            FacebookFeedAdapter fbAdapter = new FacebookFeedAdapter(mMainActivity, (ArrayList)fbFeed.getData());
                            mVh1.mFbFeedListView.setAdapter(fbAdapter);
                        }
                    }
            ).executeAsync();
        }
    }

    /**
     * Get messages from facebook Page
     */
    private void getFbPageFeed(){
        //String mPageId = "155608091155587";
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+mPageId+"/posts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Gson gson = new Gson();
                        String fbSearchJson = response.getJSONObject().toString();
                        FacebookPageObject fbPage = gson.fromJson(fbSearchJson, FacebookPageObject.class);

                        /*
                        // Message Id in page
                        Log.d(TAG, "onCompleted: SearchJson " + fbSearchJson);
                        Log.d(TAG, "onCompleted: " + fbPage.getData().toString());
                        mPostId = fbPage.getData().get(0).getId();
                        Log.d(TAG, "onCompleted: ID<><><><" + mPostId);
                        */

                        FacebookPageAdapter fbAdapter = new FacebookPageAdapter(mMainActivity, (ArrayList)fbPage.getData());
                        mVh1.mFbFeedListView.setAdapter(fbAdapter);
                    }
                }
        ).executeAsync();

    }

    /**
     * Get data from individual post
     */
    private void getFbPost(){
        mPostId = "111114815960703_117726661966185";
        //mPostId = "111114815960703_117550011983850";
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+mPostId,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        String fbPostJson = response.getJSONObject().toString();
                        Log.d(TAG, "onCompleted: //////" + fbPostJson);
                    }
                }
        ).executeAsync();

    }

    /**
     * Set listener for send button
     */
    private void setFbSendMessageButtonListener(){


//        ShareContent shareContent = new ShareContent() {
//            @Nullable
//            @Override
//            public Uri getContentUrl() {
//                return super.getContentUrl();
//            }
//        };
//        vh1.mFbSendButton.setShareContent(shareContent);
        mVh1.mFbSendButton.registerCallback(MainActivity.fbCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d(TAG, "onSuccess: ++>> FB Send Button Successful Message Sent");
                getFbFeed();
                mVh1.mFbShareTitleEditText.setText("");
                mVh1.mFbShareDescriptionEditText.setText("");
                mVh1.mFbShareUrlEditText.setText("");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    /**
     * Share button that shares content to facebook
     * ------>>> Currently not in use
     */
    private void setFbPostShareButtonListener(){
        Log.d(TAG, "setFbPostShareButtonListener: ShareButtonClicked");
        if(mVh1.mFbShareButton != null){
            mVh1.mFbShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = getTitleEditText("");
                    String description = getDescriptionEditText("");
                    String url = getUrlEditText("");
                    setupShareDialog(title, description, url);
                }
            });
        }
    }
}

