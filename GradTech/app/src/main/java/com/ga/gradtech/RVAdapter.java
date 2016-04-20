package com.ga.gradtech;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ga.gradtech.Cards.Calander.CalendarCard;
import com.ga.gradtech.Cards.Calander.CalenderCardViewHolder;
import com.ga.gradtech.Cards.Facebook.FacebookCard;
import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;
import com.ga.gradtech.Cards.Facebook.FacebookViewHolderConfigurer;
import com.ga.gradtech.Cards.NotePad.NotePadCard;
import com.ga.gradtech.Cards.NotePad.NotePadCardViewHolder;
import com.ga.gradtech.Cards.NotePad.NotepadViewHolderConfigurer;
import com.ga.gradtech.Cards.SoundCloud.SCService;
import com.ga.gradtech.Cards.SoundCloud.SoundCloud;
import com.ga.gradtech.Cards.SoundCloud.SoundCloudCard;
import com.ga.gradtech.Cards.SoundCloud.SoundCloudCardViewHolder;
import com.ga.gradtech.Cards.SoundCloud.SoundCloudConfigurer;

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


        }
    }

    private final int FACEBOOK = 0, TWITTER = 1, MEETUP = 2, SOUNDCLOUD = 3, CALENDAR = 4;
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
                viewHolder = new FacebookCardViewHolder(v1);
                break;
            case TWITTER:
                View v2 = inflater.inflate(R.layout.card_view_layout, viewGroup, false);
                viewHolder = new CardViewHolder(v2);
                break;
            case MEETUP:
                View v3 = inflater.inflate(R.layout.card_sound_cloud_layout, viewGroup, false);
                viewHolder = new CardViewHolder(v3);
                break;
            case SOUNDCLOUD:
                View v4 = inflater.inflate(R.layout.card_sound_cloud_layout, viewGroup, false);
                viewHolder = new SoundCloudCardViewHolder(v4);
                break;
            case CALENDAR:
                View v5 = inflater.inflate(R.layout.calender_card_layout, viewGroup, false);
                viewHolder = new CalenderCardViewHolder(v5);
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
                Log.d(TAG, "onBindViewHolder: ===>>> inOnBindViewHolder");
                FacebookCardViewHolder vh1 = (FacebookCardViewHolder) viewHolder;
                FacebookViewHolderConfigurer fbConfigurer = new FacebookViewHolderConfigurer(vh1, position, mainActivity);
                if(!fbConfigurer.isFacebookLoggedIn()) {
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
                configureTwitterViewHolder2(vh3, position);
                break;
            case SOUNDCLOUD:
                SoundCloudCardViewHolder vh4 = (SoundCloudCardViewHolder) viewHolder;
                configureSoundCloudViewHolder(vh4);
                break;
            case CALENDAR:
                CalenderCardViewHolder vh5 = (CalenderCardViewHolder) viewHolder;
                configureCalendarViewHolder(vh5);
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

    private void configureDefaultViewHolder(CardViewHolder vh, int position) {


    }

    private void configureTwitterViewHolder2(CardViewHolder vh2, int position) {
        Card2 card = (Card2) cards.get(position);
//
//        vh2.mCompanyName.setText("Twitter");
//        vh2.mCompanyLocation.setText("Somewhere");
//        vh2.mCompanyIcon.setImageResource(R.drawable.twitter_icon);
    }

    private void configureSoundCloudViewHolder(SoundCloudCardViewHolder vh) {
        SoundCloudConfigurer SC = new SoundCloudConfigurer(vh);
        SC.initSoundCloud();

        //SCService sc = SoundCloud.getService();

    }

    private void configureCalendarViewHolder(CalenderCardViewHolder vh) {

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
        } else if (cards.get(position) instanceof SoundCloudCard) {
            return SOUNDCLOUD;
        } else if (cards.get(position) instanceof CalendarCard) {
            return CALENDAR;
        }
        return super.getItemViewType(position);
    }
}

