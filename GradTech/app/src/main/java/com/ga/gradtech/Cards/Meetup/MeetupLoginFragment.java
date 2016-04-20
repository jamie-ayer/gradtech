package com.ga.gradtech.Cards.Meetup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Toast;

import com.ga.gradtech.ApiKeys;
import com.ga.gradtech.R;
import com.ga.gradtech.RVAdapter;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;

/**
 * Created by leisforkokomo on 4/20/16.
 */
public class MeetupLoginFragment extends Fragment {
    private final String TAG = getClass().getName();
    public final String AUTH_URL = getString(R.string.meetup_authurl);
    public final String TOKEN_URL = getString(R.string.meetup_tokenurl);

    WebView webView;
    String accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");

            if (code != null){
                new MeetupRetrieveAccessTokenTask().execute(uri);
                Log.i(TAG, "The redirect uri contained the code " + code);
//                setResult(RESULT_OK, getIntent());
            } else if (error != null) {
                Log.e(TAG, "The redirect uri contained error");
//                setResult(RESULT_CANCELLED, getIntent());
            }
            return false;
        }
    }

    private class MeetupRetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>{

        @Override
        protected Void doInBackground(Uri... params) {
            Uri uri = params[0];
            String code = uri.getQueryParameter("code");

            Log.e(TAG, "Code = " + code);

            OAuthClientRequest request = null;
            try {
                request = OAuthClientRequest.tokenLocation(TOKEN_URL)
                        .setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(
                                ApiKeys.MEETUP_CONSUMER_KEY).setClientSecret(
                                ApiKeys.MEETUP_CONSUMER_SECRET).setRedirectURI(
                                ApiKeys.MEETUP_REDIRECT_URI).setCode(code)
                        .buildBodyMessage();
                OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
                OAuthAccessTokenResponse response = oAuthClient.accessToken(request);

                Log.d(TAG, response.getAccessToken());
                Log.d(TAG, response.getExpiresIn());
                Log.d(TAG, response.getRefreshToken());
                accessToken = response.getAccessToken();

            } catch (OAuthSystemException e) {
                Log.e(TAG, "Oauth System Exception - Couldn't get access token: " + e.toString());

            } catch (OAuthProblemException e) {
                Log.e(TAG, "Oauth System Exception - Couldn't get access token" + e.getMessage());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "OAuth Problem Exception - Couldn't get access token", Toast.LENGTH_LONG).show();
                    }
                };
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);


        return v;
    }







}
