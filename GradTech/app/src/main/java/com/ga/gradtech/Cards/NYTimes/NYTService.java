//package com.ga.gradtech.Cards.NYTimes;
//
//import com.ga.gradtech.ApiKeys;
//
//import java.util.List;
//
//import retrofit.Callback;
//import retrofit.http.GET;
//import retrofit.http.Query;
//
///**
// * Created by JamieAyer on 4/18/16.
// */
//public interface NYTService {
//
//    @GET("/svc/mostpopular/v2/mostviewed/technology/1.json?api-key" + ApiKeys.NYTIME_MOST_POP_ID)
//    public void getRecentStories(Callback<List<NewYorkTimesCard>> cb);
//
//    @GET("/tracks?client_id=" + ApiKeys.NYTIME_NEWSWIRE_ID)
//    public void getArtistTracks(@Query("q") String artist, Callback<List<NewYorkTimesCard>> cb);
//
//}
