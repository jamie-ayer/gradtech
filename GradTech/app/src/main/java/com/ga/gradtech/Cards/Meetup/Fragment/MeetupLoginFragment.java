package com.ga.gradtech.Cards.Meetup.Fragment;

import android.content.Context;
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

import com.ga.gradtech.ApiKeys;
import com.ga.gradtech.Cards.Meetup.Interface.OnSuccessfulLoginListener;
import com.ga.gradtech.R;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;

/**
 * Creates the login webview frament for the Meetup card.
 * Created by leisforkokomo on 4/20/16.
 */
public class MeetupLoginFragment extends Fragment {
    //region Private Variables
    private final String TAG = getClass().getName();
    public final String AUTH_URL = "https://secure.meetup.com/oauth2/authorize";
    public final String TOKEN_URL = "https://secure.meetup.com/oauth2/access";
    private WebView loginWebView;
    private MyWebViewClient myWebViewClient;
    //endregion
    //region Public Variables
    public OnSuccessfulLoginListener mListener;
    public String accessToken;
    //endregion

    /**
     * This method inflates the views of the fragment and creates a new instance of the webViewClient
     * so websites are loaded inside the fragment instead of an internet browser window.
     * This method also makes an instance of the OauthClientRequest to authenticate users of the app.
     * This method calls the listener of the OnSuccessfulLoginLister interface.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetup_webview, container, false);
        loginWebView = (WebView) v.findViewById(R.id.meetup_webView_id);//new WebView(getContext());
        myWebViewClient = new MyWebViewClient();
        loginWebView.setWebViewClient(myWebViewClient);
        loginWebView.getSettings().setJavaScriptEnabled(true);
        OAuthClientRequest request = null;
        try {
            request = OAuthClientRequest.authorizationLocation(
                    AUTH_URL).setClientId(
                    ApiKeys.MEETUP_CONSUMER_KEY).setRedirectURI(
                    ApiKeys.MEETUP_REDIRECT_URI).buildQueryMessage();
        } catch (OAuthSystemException e) {
            Log.d(TAG, "OAuth request failed", e);
            e.printStackTrace();
        }
        loginWebView.loadUrl(request.getLocationUri() + "&response_type=code&set_mobile=on");
        myWebViewClient.onPageFinished(loginWebView, ApiKeys.MEETUP_REDIRECT_URI + "&client_id=" + accessToken + "&response_type=code&set_mobile=on");
        return v;
    }

    /**
     * This method creates an innerclass for a subclass of the WebViewClient. When the specific page
     * is finished loading, the mListener is called to send the accessToken to the MainActivity.
     * shouldOverrideUrlLoading verifies if the redirect uri contains an error or code.
     */
    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (accessToken != null){
                mListener.onSuccessfulLogin(accessToken);
                Log.i(TAG, "The access token is " + accessToken);
            } else {
                String crapToken = "crap";
                Log.i(TAG, "The access token is " + crapToken);
            }
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");
            if (code != null){
                new MeetupRetrieveAccessTokenTask().execute(uri);
                Log.i(TAG, "The redirect uri contained the code " + code);
            } else if (error != null) {
                Log.e(TAG, "The redirect uri contained error");
            }
            return false;
        }
    }

    /**
     * This method creates an AsyncTask to handle user authentication.
     * It can return the accessToken, expiration time, and refresh token.
     */
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
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * This method instantiates an instance of the OnSuccessfulLoginListener and connects it to an activity.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSuccessfulLoginListener) getActivity();
        } catch (ClassCastException e){
            Log.e(TAG, "Must implement OnSuccessfulLoginListener");
        }
    }


}
