package com.ga.gradtech;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;
import com.ga.gradtech.Cards.Facebook.FacebookViewHolderConfigurer;
import com.ga.gradtech.Cards.Meetup.MeetupCard;
import com.ga.gradtech.Cards.Meetup.Fragment.MeetupLoginFragment;
import com.ga.gradtech.Cards.Meetup.OnSuccessfulLoginListener;
import com.ga.gradtech.Cards.NotePad.NotePadCardViewHolder;
import com.ga.gradtech.Cards.NotePad.NotepadViewHolderConfigurer;


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

    private final int FACEBOOK = 0, TWITTER = 1, MEETUP = 2, TRELLO = 3, GITHUB = 4;
    private final int GLASSDOOR = 5, LINKEDIN = 6, YELP = 8, NOTEPAD = 9;

    List<Object> cards;
    AppCompatActivity mainActivity;
    FragmentManager meetupFragmentManager;

    RVAdapter(List<Object> cards, AppCompatActivity activity, FragmentManager fragmentManager1) {
        this.mainActivity = activity;
        this.cards = cards;
        this.meetupFragmentManager = fragmentManager1;
    }

    /**
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
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
            case MEETUP:
                View v3 = inflater.inflate(R.layout.card_meetup_layout, viewGroup, false);
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

    /**
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case FACEBOOK:
                Log.d(TAG, "onBindViewHolder: ===>>> inOnBindViewHolder");
                FacebookCardViewHolder vh1 = (FacebookCardViewHolder) viewHolder;
                FacebookViewHolderConfigurer fbConfigurer = new FacebookViewHolderConfigurer(vh1, position, mainActivity);
                if(!fbConfigurer.isFacebookLoggedIn()){
                    Log.d(TAG, "onBindViewHolder: ====>>> Facebook logged in");
                    fbConfigurer.initFacebookLogin();
                }
                fbConfigurer.facebookShare();
                fbConfigurer.facebookGetFeed();
                break;
            case TWITTER:
                CardViewHolder vh2 = (CardViewHolder) viewHolder;
                configureTwitterViewHolder2(vh2, position);
                break;
            case MEETUP:
                CardViewHolder vh3 = (CardViewHolder) viewHolder;
                configureMeetupViewHolder2(vh3, position);
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
                NotepadViewHolderConfigurer notePadConfigurer = new NotepadViewHolderConfigurer(vh9, position, mainActivity);
                notePadConfigurer.initNotePad();
                notePadConfigurer.setNotePadEditButtonListener();
                notePadConfigurer.setNotePadSaveButtonListener();
                break;
            default:
                CardViewHolder vh = (CardViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    /**
     *
     * @param vh
     * @param position
     */
    private void configureDefaultViewHolder(CardViewHolder vh, int position) {
    }

    /**
     *
     * @param vh2
     * @param position
     */
    private void configureTwitterViewHolder2(CardViewHolder vh2, int position) {
        Card2 card = (Card2) cards.get(position);

//        vh2.mCompanyName.setText("Twitter");
//        vh2.mCompanyLocation.setText("Somewhere");
//        vh2.mCompanyIcon.setImageResource(R.drawable.twitter_icon);
    }

    /**
     *
     * @param vh3
     * @param position
     */
    private void configureMeetupViewHolder2(CardViewHolder vh3, int position) {
        Log.d(TAG, "configureMeetupViewHolder2: inside damn holder");
        MeetupCard card = (MeetupCard) cards.get(position);

        MeetupLoginFragment meetupLoginFragment = new MeetupLoginFragment();
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.meetup_container_id, meetupLoginFragment);
        fragmentTransaction.commit();




        /*
        MeetupResultsFragment meetupResultsFragment = new MeetupResultsFragment();

        */

    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return cards.size();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        return position;
        /*
        if (cards.get(position) instanceof FacebookCard) {
            return FACEBOOK;
        } else if (cards.get(position) instanceof Card2) {
            return TWITTER;
        }else if (cards.get(position) instanceof NotePadCard){
            return NOTEPAD;
        } else if (cards.get(position) instanceof MeetupCard){
            return MEETUP;
        }
        return super.getItemViewType(position);
        */
    }
}

