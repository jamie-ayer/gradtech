package com.ga.gradtech.Cards.Facebook;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
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
import com.ga.gradtech.R;
import com.ga.gradtech.RVAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by samsiu on 4/20/16.
 */
public class FacebookViewHolderConfigurer {
    private static final String TAG = FacebookViewHolderConfigurer.class.getCanonicalName();


    FacebookCardViewHolder vh1;
    int position;
    Activity mainActivity;

    public FacebookViewHolderConfigurer(FacebookCardViewHolder vh1, int position, Activity mainActivity) {
        this.vh1 = vh1;
        this.position = position;
        this.mainActivity = mainActivity;
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

    public void initFbLogin(){
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
                                setFbListViewVisibility();
                                setFbLoginButtonVisibility();
                                getFbFeed();
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
                getFbFeed();
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

                            Log.d(TAG, "onCompleted: " + fbFeed.getData().toString());

                            FacebookFeedAdapter fbAdapter = new FacebookFeedAdapter(mainActivity, (ArrayList)fbFeed.getData());
                            vh1.mFbFeedListView.setAdapter(fbAdapter);
                        }
                    }
            ).executeAsync();
        }
    }
}
