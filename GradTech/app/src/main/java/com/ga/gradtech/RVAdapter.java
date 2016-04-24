package com.ga.gradtech;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;

import android.support.v4.app.FragmentTransaction;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ga.gradtech.Cards.CalendarProvider.CalendarProviderCardViewHolder;
import com.ga.gradtech.Cards.CalendarProvider.CalendarViewHolderConfigurer;
import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;
import com.ga.gradtech.Cards.Facebook.FacebookViewHolderConfigurer;

import com.ga.gradtech.Cards.Meetup.Fragment.MeetupLoginFragment;

import com.ga.gradtech.Cards.Calander.CalenderCardViewHolder;


import com.ga.gradtech.Cards.NotePad.NotePadCardViewHolder;
import com.ga.gradtech.Cards.NotePad.NotepadViewHolderConfigurer;
import com.ga.gradtech.Cards.Twitter.TwitterFragment;
import com.ga.gradtech.Cards.Twitter.TwitterViewHolder;

import com.ga.gradtech.Cards.SoundCloud.SoundCloudCardViewHolder;
import com.ga.gradtech.Cards.SoundCloud.SoundCloudConfigurer;


/**
 * Created by JamieAyer on 4/18/16.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = RVAdapter.class.getCanonicalName();
    /**
     * PlaceHolder for now
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardViewHolder(View itemView) {
            super(itemView);
        }
    }

    private final int FACEBOOK = 0, TWITTER = 1, MEETUP = 2, SOUNDCLOUD = 3, CALENDAR = 4;
    private final int GLASSDOOR = 9, LINKEDIN = 7, YELP = 8, NOTEPAD = 5, CALENDAR2 = 6;

    int numCards;
    Activity mainActivity;
    AppCompatActivity appCompatActivityMeetup;

    android.support.v4.app.FragmentManager fragmentManagerTwitter;
    android.support.v4.app.FragmentManager meetupFragmentManager;

    RVAdapter(int numCards, Activity activity, AppCompatActivity appCompatActivity, android.support.v4.app.FragmentManager fragmentManager2, android.support.v4.app.FragmentManager fragmentManager1) {
        this.mainActivity = activity;
        this.appCompatActivityMeetup = appCompatActivity;
        this.numCards = numCards;
        this.fragmentManagerTwitter = fragmentManager2;
        this.meetupFragmentManager = fragmentManager1;
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
                View v2 = inflater.inflate(R.layout.twitter_card_layout, viewGroup, false);
                viewHolder = new TwitterViewHolder(v2);
                break;
            case MEETUP:
                View v3 = inflater.inflate(R.layout.card_meetup_layout, viewGroup, false);
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
            case NOTEPAD:
                View v6 = inflater.inflate(R.layout.card_notepad_layout, viewGroup, false);
                viewHolder = new NotePadCardViewHolder(v6);
                break;
            case CALENDAR2:
                View v7 = inflater.inflate(R.layout.card_calendar_layout, viewGroup, false);
                viewHolder = new CalendarProviderCardViewHolder(v7);
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
        switch (position) {
            case FACEBOOK:
                FacebookCardViewHolder vh1 = (FacebookCardViewHolder) viewHolder;
                configureFacebookViewHolder(vh1);
                break;
            case TWITTER:
                TwitterViewHolder vh2 = (TwitterViewHolder) viewHolder;
                configureTwitterViewHolder2(vh2, position);
                break;
            case MEETUP:
                CardViewHolder vh3 = (CardViewHolder) viewHolder;
                configureMeetupViewHolder2(vh3, position);
                break;
            case SOUNDCLOUD:
                SoundCloudCardViewHolder vh4 = (SoundCloudCardViewHolder) viewHolder;
                configureSoundCloudViewHolder(vh4);
                break;
            case CALENDAR:
                CalenderCardViewHolder vh5 = (CalenderCardViewHolder) viewHolder;
                configureCalendarViewHolder(vh5);
                break;
            case NOTEPAD:
                NotePadCardViewHolder vh6 = (NotePadCardViewHolder) viewHolder;
                configureNotePadViewHolder(vh6);
                break;
            case CALENDAR2:
                CalendarProviderCardViewHolder vh7 = (CalendarProviderCardViewHolder) viewHolder;
                configureCalendarProviderViewHolder(vh7);
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
    private void configureTwitterViewHolder2(TwitterViewHolder vh2, int position) {
    }


    public TwitterFragment getTwitterFragment() {
        return (TwitterFragment) fragmentManagerTwitter.findFragmentById(R.id.twitter_fragment_id);
    }

    /**
     *
     * @param vh3
     * @param position
     */
    private void configureMeetupViewHolder2(CardViewHolder vh3, int position) {
        Log.d(TAG, "configureMeetupViewHolder2: inside damn holder");
        MeetupLoginFragment meetupLoginFragment = new MeetupLoginFragment();
        FragmentTransaction fragmentTransaction = appCompatActivityMeetup.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.meetup_container_id, meetupLoginFragment);
        fragmentTransaction.commit();
    }

    private void configureSoundCloudViewHolder(SoundCloudCardViewHolder vh) {
        SoundCloudConfigurer SC = new SoundCloudConfigurer(vh);
        SC.initSoundCloud(mainActivity);

    }

    private void configureFacebookViewHolder(FacebookCardViewHolder vh1){
        FacebookViewHolderConfigurer fbConfigurer = new FacebookViewHolderConfigurer(vh1, mainActivity);
        Log.d(TAG, "onBindViewHolder: ====>>> Facebook logged in");
        fbConfigurer.initFacebookCardView();
        if (!fbConfigurer.isFacebookLoggedIn()) {
            Log.d(TAG, "onBindViewHolder: ====>>> Facebook Not logged in");
            fbConfigurer.initFbLoginButton();
        }
    }

    private void configureCalendarProviderViewHolder(CalendarProviderCardViewHolder vh7){
        CalendarViewHolderConfigurer calendarConfigurer = new CalendarViewHolderConfigurer(vh7, mainActivity);
        calendarConfigurer.initCalendarProviderCardView();
    }

    private void configureNotePadViewHolder(NotePadCardViewHolder vh6){
        NotepadViewHolderConfigurer notePadConfigurer = new NotepadViewHolderConfigurer(vh6, mainActivity);
        notePadConfigurer.initNotePad();
    }


    private void configureCalendarViewHolder(CalenderCardViewHolder vh) {

    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return numCards;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return FACEBOOK;
        } else if (position == 1) {
            return TWITTER;
        } else if (position == 2) {
            return MEETUP;
        } else if (position == 3) {
            return SOUNDCLOUD;
        } else if (position == 4) {
            return CALENDAR;
        } else if (position == 5) {
            return NOTEPAD;
        }else if (position == 6){
            return CALENDAR2;
        }
        return super.getItemViewType(position);
    }
}

