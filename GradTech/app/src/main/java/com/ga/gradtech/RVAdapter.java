package com.ga.gradtech;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JamieAyer on 4/18/16.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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
    private final int GLASSDOOR = 5, LINKEDIN = 6, YELP = 8;

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
                View v1 = inflater.inflate(R.layout.card_view_layout, viewGroup, false);
                viewHolder = new CardViewHolder(v1); ///WHERE THE VIEW HOLDER FIRST COMES INTO PLAY
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
                CardViewHolder vh1 = (CardViewHolder) viewHolder;
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
            default:
                CardViewHolder vh = (CardViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(CardViewHolder vh, int position) {


    }

    private void configureFacebookViewHolder1(CardViewHolder vh1, int position) {
        Card card = (Card) cards.get(position);

        vh1.mCompanyName.setText("Facebook");
        vh1.mCompanyLocation.setText("Somewhere");
        vh1.mCompanyIcon.setImageResource(R.drawable.facebook_icon);

    }

    private void configureTwitterViewHolder2(CardViewHolder vh2, int position) {
        Card2 card = (Card2) cards.get(position);

        vh2.mCompanyName.setText("Twitter");
        vh2.mCompanyLocation.setText("Somewhere");
        vh2.mCompanyIcon.setImageResource(R.drawable.twitter_icon);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (cards.get(position) instanceof Card) {
            return FACEBOOK;
        } else if (cards.get(position) instanceof Card2) {
            return TWITTER;
        }
        return super.getItemViewType(position);
    }

}
