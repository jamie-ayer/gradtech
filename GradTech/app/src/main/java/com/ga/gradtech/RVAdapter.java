package com.ga.gradtech;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.ga.gradtech.Cards.Facebook.FacebookCard;
import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;
import com.ga.gradtech.Cards.Facebook.FacebookFeedObject;
import com.ga.gradtech.Cards.NotePad.NotePadCard;
import com.ga.gradtech.Cards.NotePad.NotePadCardViewHolder;
import com.ga.gradtech.Cards.NotePad.NotepadSQLiteHelper;
import com.google.gson.Gson;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by JamieAyer on 4/18/16.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static String TAG = RVAdapter.class.getCanonicalName();

    /**
     * PlaceHolder for now
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mCompanyName;
        TextView mCompanyLocation;
        ImageView mCompanyIcon;


        CardViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView)itemView.findViewById(R.id.cv);
            mCompanyName = (TextView)itemView.findViewById(R.id.company_name);
            mCompanyLocation = (TextView)itemView.findViewById(R.id.company_location);
            mCompanyIcon = (ImageView)itemView.findViewById(R.id.company_photo);

        }
    }

    private final int FACEBOOK = 0, TWITTER = 1, TECHCRUNCH = 2, TRELLO = 3, GITHUB = 4;
    private final int GLASSDOOR = 5, LINKEDIN = 6, YELP = 8, NOTEPAD = 9;

    List<Object> cards;
    Activity mainActivity;

    RVAdapter(List<Object> cards, Activity activity) {
        this.mainActivity = activity;
        this.cards = cards;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case FACEBOOK:
                View v1 = inflater.inflate(R.layout.card_facebook_layout, viewGroup, false);
                viewHolder = new FacebookCardViewHolder(v1); ///WHERE THE VIEW HOLDER FIRST COMES INTO PLAY
                break;
            case TWITTER:
                View v2 = inflater.inflate(R.layout.card_view_layout, viewGroup, false);
                viewHolder = new CardViewHolder(v2);
                break;
            case TECHCRUNCH:
                View v3 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v3);
                break;
            case TRELLO:
                View v4 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v4);
                break;
            case GITHUB:
                View v5 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v5);
                break;
            case GLASSDOOR:
                View v6 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v6);
                break;
            case LINKEDIN:
                View v7 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v7);
                break;
            case YELP:
                View v8 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new CardViewHolder(v8);
                break;
            case NOTEPAD:
                View v9 = inflater.inflate(R.layout.card_notepad_layout, viewGroup, false);
                viewHolder = new NotePadCardViewHolder(v9);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new CardViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case FACEBOOK:
                FacebookCardViewHolder vh1 = (FacebookCardViewHolder) viewHolder;
                configureFacebookViewHolder1(vh1, position);
                break;
            case TWITTER:
                CardViewHolder vh2 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh2, position);
                break;
            case TECHCRUNCH:
                CardViewHolder vh3 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh3, position);
                break;
            case TRELLO:
                CardViewHolder vh4 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh4, position);
                break;
            case GITHUB:
                CardViewHolder vh5 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh5, position);
                break;
            case GLASSDOOR:
                CardViewHolder vh6 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh6, position);
                break;
            case LINKEDIN:
                CardViewHolder vh7 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh7, position);
                break;
            case YELP:
                CardViewHolder vh8 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh8, position);
                break;
            case NOTEPAD:
                NotePadCardViewHolder vh9 = (NotePadCardViewHolder) viewHolder;
                configureNotePadViewHolder(vh9, position);
                break;
            default:
                CardViewHolder vh = (CardViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(CardViewHolder vh, int position) {


    }

    private void configureFacebookViewHolder1(final FacebookCardViewHolder vh1, int position) {
//        FacebookCard card = (FacebookCard) cards.get(position);

        boolean loggedIn = isFacebookLoggedIn();
        if(!loggedIn){
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

        if(vh1.mFbGetFeedButton != null){
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

                                    for(FacebookFeedObject.FbData data : fbFeed.getData()){
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

    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }


    private void configureTwitterViewHolder2(CardViewHolder vh2, int position) {
        Card2 card = (Card2) cards.get(position);

        vh2.mCompanyName.setText("Twitter");
        vh2.mCompanyLocation.setText("Somewhere");
        vh2.mCompanyIcon.setImageResource(R.drawable.twitter_icon);
    }


    private void configureNotePadViewHolder(final NotePadCardViewHolder vh9, int position){
   //     NotePadCard card = (NotePadCard) cards.get(position);

        final NotepadSQLiteHelper db = new NotepadSQLiteHelper(mainActivity);
        //db.insertNotepadItem(1, "FirstNote", "This is my first test note.");

        Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
        cursor.moveToFirst();
        String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));
        Log.d(TAG, String.format("onCreate: description: %s ", description));

        vh9.mCurrentText.setText(description);

        vh9.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editTextVisibility = vh9.mEditText.getVisibility();
                Log.d(TAG, "onClick: ==>>> Visibility " + editTextVisibility);

                if (editTextVisibility == 4) { // INVISIBLE
                    //String curText = currentText.getText().toString();
                    Cursor cursor = NotepadSQLiteHelper.getInstance(mainActivity).getNotepadItem();
                    cursor.moveToFirst();
                    String description = cursor.getString(cursor.getColumnIndex(NotepadSQLiteHelper.COL_NOTEPAD_DESCRIPTION));

                    Log.d(TAG, "onClick: Edit Clicked ===>>> INVISIBLE 4 - Bring to visible " + description);
                    vh9.mEditText.setText(description);
                    cursor.close();
                    db.close();
                } else if (editTextVisibility == 0) { // VISIBLE
                    vh9.mEditText.setText(vh9.mEditText.getText().toString());
                    Log.d(TAG, "onClick: =====>>>>>  VISIBLE 0");
                }

                vh9.mCurrentText.setVisibility(View.INVISIBLE);
                vh9.mEditText.setVisibility(View.VISIBLE);
            }
        });

        vh9.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edText = vh9.mEditText.getText().toString();
                Log.d(TAG, "onClick: ===>>>> SaveButton " + edText);
                vh9.mCurrentText.setText(edText);
                db.updateNotepadeItem(1, "FirstNote", edText);
                vh9.mCurrentText.setVisibility(View.VISIBLE);
                vh9.mEditText.setVisibility(View.INVISIBLE);
                db.close();

            }
        });

        cursor.close();
    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (cards.get(position) instanceof FacebookCard) {
            return FACEBOOK;
        } else if (cards.get(position) instanceof Card2) {
            return TWITTER;
        }else if (cards.get(position) instanceof NotePadCard){
            return NOTEPAD;
        }

        return super.getItemViewType(position);
    }


}

