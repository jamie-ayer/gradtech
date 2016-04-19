//package com.ga.gradtech.Cards.NYTimes;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ga.gradtech.R;
//
//import java.util.List;
//
///**
// * Created by JamieAyer on 4/18/16.
// */
//public class NYTArticleAdapter {
//
//    private Context mContext;
//    private List<NewYorkTimesCard> mArticles;
//
//    public NYTArticleAdapter(Context context, List<NewYorkTimesCard> tracks) {
//        mContext = context;
//        mArticles = tracks;
//    }
//
//    @Override
//    public int getCount() {
//        return mArticles.size();
//    }
//
//    @Override
//    public NewYorkTimesCard getItem(int position) {
//        return mArticles.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        NewYorkTimesCard article = getItem(position);
//
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.nytimes_card_layout, parent, false);
//            holder = new ViewHolder();
//            holder.trackImageView = (ImageView) convertView.findViewById(R.id.track_image);
//            holder.titleTextView = (TextView) convertView.findViewById(R.id.track_title);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.titleTextView.setText(article.getTitle());
//
//        // Trigger the download of the URL asynchronously into the image view.
//        //Picasso.with(mContext).load(article.getArtworkURL()).into(holder.trackImageView);
//
//        return convertView;
//    }
//
//    static class ViewHolder {
//        ImageView trackImageView;
//        TextView titleTextView;
//    }
//}
