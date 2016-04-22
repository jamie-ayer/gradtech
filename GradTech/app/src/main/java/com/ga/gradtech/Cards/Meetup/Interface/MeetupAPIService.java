package com.ga.gradtech.Cards.Meetup.Interface;

import com.ga.gradtech.Cards.Meetup.Model.ActivityFeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by leisforkokomo on 4/21/16.
 */
public interface MeetupAPIService {

    @GET("2/open_events")
    Call<ActivityFeed> listFeed(@Header("Authorization") String auth, @Query("zip") int zip, @Query("topic") String topic, @Query("category") int category, @Query("page") int count);
}


// https://api.meetup.com/ BASE_URL
// 2/open_events
// ?&sign=true&photo-host=public
// &zip=94104
// &topic=android
// &category=34
// &page=6