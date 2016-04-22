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
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by samsiu on 4/20/16.
 */
public class FacebookViewHolderConfigurer {
    private static final String TAG = FacebookViewHolderConfigurer.class.getCanonicalName();


    String mPostId;

    FacebookCardViewHolder vh1;
    Activity mainActivity;

    public FacebookViewHolderConfigurer(FacebookCardViewHolder vh1, Activity mainActivity) {
        this.vh1 = vh1;
        this.mainActivity = mainActivity;
    }

    public void initFacebookCardView(){
        setFbLoginButtonVisibility();
        setFbListViewVisibility();
        setFbShareViewVisibility();

        setFbPostShareButtonListener();
        setFbUpdateFeedButtonListener();
        getFbFeed();
    }

    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }

    public void setFbLoginButtonVisibility(){
        if(isFacebookLoggedIn()){
            vh1.mFbLoginButton.setVisibility(View.GONE);
        }else{
            vh1.mFbLoginButton.setVisibility(View.VISIBLE);
        }
    }

    public void setFbListViewVisibility(){
        if(isFacebookLoggedIn()){
            vh1.mFbFeedListView.setVisibility(View.VISIBLE);
        }else{
            vh1.mFbFeedListView.setVisibility(View.GONE);
        }
    }

    public void setFbShareViewVisibility(){
        if(isFacebookLoggedIn()){
            vh1.mFbShareButton.setVisibility(View.VISIBLE);
            vh1.mFbShareDescriptionEditText.setVisibility(View.VISIBLE);
            vh1.mFbShareTitleEditText.setVisibility(View.VISIBLE);
            vh1.mFbShareUrlEditText.setVisibility(View.VISIBLE);
            vh1.mFbGetFeedButton.setVisibility(View.VISIBLE);
        }else{
            vh1.mFbShareButton.setVisibility(View.GONE);
            vh1.mFbShareDescriptionEditText.setVisibility(View.GONE);
            vh1.mFbShareTitleEditText.setVisibility(View.GONE);
            vh1.mFbShareUrlEditText.setVisibility(View.GONE);
            vh1.mFbGetFeedButton.setVisibility(View.GONE);
        }
    }

    public void initFbLoginButton(){
        vh1.mFbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ===>>> Facebook Button Clicked");
                Collection<String> permissions = Arrays.asList("public_profile",
                        "user_friends",
                        "user_status",
                        "user_posts");
                //LoginManager.getInstance().logInWithReadPermissions(mainActivity, permissions);
                LoginManager.getInstance().registerCallback(MainActivity.callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "onSuccess: ====>>> FBLogin Successful");
                                initFacebookCardView();

                                Toast toast = Toast.makeText(mainActivity.getApplicationContext(), "Successful FB Login", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onCancel() {
                                Log.d(TAG, "onCancel: ===>>> FBLogin Cancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.d(TAG, "onError: ======>>>> FBlogin Error");
                            }
                        }
                );
            }
        });
    }

    public void setFbPostShareButtonListener(){
        Log.d(TAG, "setFbPostShareButtonListener: ShareButtonClicked");
        final ShareDialog shareDialog = new ShareDialog(mainActivity);
        if(vh1.mFbShareButton != null){
            vh1.mFbShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = vh1.mFbShareTitleEditText.getText().toString();
                    String description = vh1.mFbShareDescriptionEditText.getText().toString();
                    String url = vh1.mFbShareUrlEditText.getText().toString();
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentTitle(title)
                                .setContentDescription(description)
                                .setContentUrl(Uri.parse(url))
                                .build();
                        shareDialog.show(content);
                        shareDialog.registerCallback(MainActivity.callbackManager, new FacebookCallback<Sharer.Result>() {
                            @Override
                            public void onSuccess(Sharer.Result result) {
                                getFbFeed();
                                vh1.mFbShareTitleEditText.setText("");
                                vh1.mFbShareDescriptionEditText.setText("");
                                vh1.mFbShareUrlEditText.setText("");
                            }

                            @Override
                            public void onCancel() {
                                getFbFeed();
                            }

                            @Override
                            public void onError(FacebookException error) {
                                getFbFeed();
                            }
                        });
                    }
                }
            });
        }
    }

    public void setFbUpdateFeedButtonListener(){
        vh1.mFbGetFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: UpdateFeedClicked");
                getFbFeed();
                //getFbPost();
            }
        });
    }

    public void getFbFeed() {
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

                            Log.d(TAG, "onCompleted: FeedJson " + fbFeedJson);
                            Log.d(TAG, "onCompleted: " + fbFeed.getData().toString());


                            mPostId = fbFeed.getData().get(0).getId();


                            Log.d(TAG, "onCompleted: ID<><><><" + mPostId);
                            FacebookFeedAdapter fbAdapter = new FacebookFeedAdapter(mainActivity, (ArrayList)fbFeed.getData());
                            vh1.mFbFeedListView.setAdapter(fbAdapter);
                        }
                    }
            ).executeAsync();
        }
    }

    public void getFbPost(){
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

}
