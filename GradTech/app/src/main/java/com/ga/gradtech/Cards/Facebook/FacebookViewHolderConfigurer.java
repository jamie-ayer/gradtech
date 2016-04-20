package com.ga.gradtech.Cards.Facebook;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ga.gradtech.MainActivity;
import com.google.gson.Gson;

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


    public void initFacebookLogin(){
        Collection<String> permissions = Arrays.asList("public_profile",
                "user_friends",
                "user_status",
                "user_posts");
        LoginManager.getInstance().logInWithReadPermissions(mainActivity, permissions);
        LoginManager.getInstance().registerCallback(MainActivity.callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast toast = Toast.makeText(mainActivity.getApplicationContext(), "SuccessfulLogin", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                }
        );
    }

    public void facebookShare(){
        final ShareDialog shareDialog = new ShareDialog(mainActivity);
        if(vh1.mFbShareButton != null){
            vh1.mFbShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentTitle("Testing")
                                .setContentDescription("This is my test share from app")
                                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                                .build();
                        shareDialog.show(content);
                    }
                }
            });
        }
    }

    public void facebookGetFeed() {
        if (vh1.mFbGetFeedButton != null) {
            vh1.mFbGetFeedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

                                    Log.d(TAG, "onCompleted: " + response.getJSONObject().toString());

                                    for (FacebookFeedObject.FbData data : fbFeed.getData()) {
                                        vh1.mFbFeedTextView.setText(data.getMessage() +
                                                "\n" +
                                                vh1.mFbFeedTextView.getText().toString());
                                    }
                                }
                            }
                    ).executeAsync();
                }
            });
        }
    }
}
