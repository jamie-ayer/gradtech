package com.ga.gradtech;

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

    RVAdapter(List<Object> cards) {

        this.cards = cards;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case FACEBOOK:
                View v1 = inflater.inflate(R.layout.card_view_layout, viewGroup, false);
                viewHolder = new Card(v1);
                break;
            case TWITTER:
                View v2 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v2);
                break;
            case TECHCRUNCH:
                View v3 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v3);
                break;
            case TRELLO:
                View v4 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v4);
                break;
            case GITHUB:
                View v5 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v5);
                break;
            case GLASSDOOR:
                View v6 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v6);
                break;
            case LINKEDIN:
                View v7 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v7);
                break;
            case YELP:
                View v8 = inflater.inflate(R.layout.card_view_layout_2, viewGroup, false);
                viewHolder = new Card2(v8);
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
                Card vh1 = (Card) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case TWITTER:
                Card2 vh2 = (Card2) viewHolder;
                configureViewHolder2(vh2);
                break;
            case TECHCRUNCH:
                Card2 vh3 = (Card2) viewHolder;
                configureViewHolder2(vh3);
                break;
            case TRELLO:
                Card2 vh4 = (Card2) viewHolder;
                configureViewHolder2(vh4);
                break;
            case GITHUB:
                Card2 vh5 = (Card2) viewHolder;
                configureViewHolder2(vh5);
                break;
            case GLASSDOOR:
                Card2 vh6 = (Card2) viewHolder;
                configureViewHolder2(vh6);
                break;
            case LINKEDIN:
                Card2 vh7 = (Card2) viewHolder;
                configureViewHolder2(vh7);
                break;
            case YELP:
                Card2 vh8 = (Card2) viewHolder;
                configureViewHolder2(vh8);
                break;
            default:
                CardViewHolder vh = (CardViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(CardViewHolder vh, int position) {


    }

    private void configureViewHolder1(Card vh1, int position) {
        Card card = (Card) cards.get(position);
        if (card != null) {
            vh1.getmCompanyName().setText("Facebook");
            vh1.getmLocation().setText("Somewhere");
            vh1.getPhotoId().setImageResource(R.drawable.twitter_icon);
        }
    }

    private void configureViewHolder2(Card2 vh2) {
        vh2.getmCompanyName();
        vh2.getmLocation();
        vh2.getPhotoId().setImageResource(R.drawable.twitter_icon);
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
