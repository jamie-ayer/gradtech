package com.ga.gradtech.Cards.SoundCloud;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by JamieAyer on 4/14/16.
 */
public interface SCService {

    @GET("/tracks?client_id=" + Config.CLIENT_ID)
    public void getRecentTracks(@Query("created_at[from]") String date, Callback<List<Track>> cb);

    @GET("/tracks?client_id=" + Config.CLIENT_ID)
    public void getArtistTracks(@Query("q") String artist, Callback<List<Track>> cb);

}