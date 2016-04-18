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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder>{

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

    List<Card> cards;

    RVAdapter(List<Card> cards){
        this.cards = cards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.mCompanyName.setText(cards.get(position).mCompanyName);
        holder.mCompanyLocation.setText(cards.get(position).mLocation);
        holder.mCompanyIcon.setImageResource(cards.get(position).photoId);

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
