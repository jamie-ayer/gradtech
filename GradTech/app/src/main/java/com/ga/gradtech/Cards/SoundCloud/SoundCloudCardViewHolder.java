package com.ga.gradtech.Cards.SoundCloud;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.ga.gradtech.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JamieAyer on 4/20/16.
 */
public class SoundCloudCardViewHolder extends RecyclerView.ViewHolder {

    ListView mListView;
    ImageView mSelectedTrackImage;
    TextView mSelectedTrackTitle;
    ImageView mPlayerControl;
    EditText mSearchBar;
    ImageButton mSearchButton;

    public SoundCloudCardViewHolder(View itemView) {
        super(itemView);
        mListView = (ListView)itemView.findViewById(R.id.track_list_view);
        mSelectedTrackImage = (ImageView)itemView.findViewById(R.id.selected_track_image);
        mSelectedTrackTitle = (TextView)itemView.findViewById(R.id.selected_track_title);
        mPlayerControl = (ImageView)itemView.findViewById(R.id.player_control);
        mSearchBar = (EditText)itemView.findViewById(R.id.search_bar);
        mSearchButton = (ImageButton)itemView.findViewById(R.id.search_button);
    }

}
