package com.ga.gradtech.Cards.SoundCloud;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ga.gradtech.MainActivity;
import com.ga.gradtech.RVAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ga.gradtech.R;

/**
 * Created by JamieAyer on 4/20/16.
 */
public class SoundCloudConfigurer {

    private MediaPlayer mMediaPlayer;

    SoundCloudCardViewHolder viewHolder;


    private List<Track> mListItems;
    private SCTrackAdapter mAdapter;

    private ListView mListView;

    public SoundCloudConfigurer(SoundCloudCardViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        this.mListView = viewHolder.mListView;
    }

    public void initSoundCloud() {

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                togglePlayPause();
            }
        });

        setOnClicks();

        mListItems = new ArrayList<Track>();
        mAdapter = new SCTrackAdapter(viewHolder.mListView.getContext(), mListItems);
        mListView.setAdapter(mAdapter);

        SCService scService = SoundCloud.getService();
        scService.getRecentTracks(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                loadTracks(tracks);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void setOnClicks() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track track = mListItems.get(position);

                viewHolder.mSelectedTrackTitle.setText(track.getTitle());
                Picasso.with(viewHolder.mSelectedTrackTitle.getContext()).load(track.getArtworkURL()).into(viewHolder.mSelectedTrackImage);

                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();
                }

                try {
                    mMediaPlayer.setDataSource(track.getStreamURL() + "?client_id=" + Config.CLIENT_ID);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        viewHolder.mPlayerControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewHolder.mPlayerControl.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            }
        });

        viewHolder.mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SCService scService = SoundCloud.getService();
                scService.getArtistTracks(viewHolder.mSearchBar.getText().toString(), new Callback<List<Track>>() {
                    @Override
                    public void success(List<Track> tracks, Response response) {
                        loadTracks(tracks);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Log.d("Retrofit ", "Error: " + error);
                    }
                });

            }
        });
    }

    private void loadTracks(List<Track> tracks) {
        mListItems.clear();
        mListItems.addAll(tracks);
        mAdapter.notifyDataSetChanged();
    }

    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            viewHolder.mPlayerControl.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        } else {
            mMediaPlayer.start();
            viewHolder.mPlayerControl.setImageResource(R.drawable.ic_pause_white_24dp);
        }
    }
}
