package com.ga.gradtech.Cards.Meetup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.ga.gradtech.Cards.Meetup.Interface.MeetupAPIService;
import com.ga.gradtech.Cards.Meetup.MeetupAdapter;
import com.ga.gradtech.Cards.Meetup.Model.ActivityFeed;
import com.ga.gradtech.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates the fragment that displays tge results of the Meetup API call.
 * Created by leisforkokomo on 4/20/16.
 */
public class MeetupResultsFragment extends Fragment{
    //region Private Variables
    private final static String TAG = "MeetupResultsFragment";
    private String accessToken;
    private ListView listView;
    private ArrayList activityFeedArrayList;
    private MeetupAdapter meetupAdapter;
    private Retrofit retrofit;
    private MeetupAPIService service;
    //endregion

    /**
     * This method gets and returns the accessToken.
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * This method sets the accessToken.
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Instances of the Retrofit object and MeetupAPIService are instantiated in this method. The base URL is also defined here.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.meetup.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MeetupAPIService.class);
    }

    /**
     * This method makes the retrofit call that authenticates the user via Oauth2 on a background thread
     * and sets up what should happen upon success or failure.
     * This method also inflates the views of the fragment.
     * The listView is initialized here.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the inflated view.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetup_results, container, false);
        listView = (ListView) v.findViewById(R.id.meetup_resultsFragment_listView_id);
        if (accessToken.equals("crap")){
            Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
        }
        Call<ActivityFeed> activityFeedCall = service.listFeed("Bearer " + accessToken, 94014, "android", 34, 6);
        activityFeedCall.enqueue(new Callback<ActivityFeed>() {
            @Override
            public void onResponse(Call<ActivityFeed> call, Response<ActivityFeed> response) {
                ActivityFeed activityFeedData = response.body();
                Log.e(TAG, "Thank Jesus Christ, it works! The array has a length of " + activityFeedData.getResults().length);
                activityFeedArrayList = new ArrayList(Arrays.asList(activityFeedData.getResults()));
                if (activityFeedArrayList != null){
                    meetupAdapter = new MeetupAdapter(getContext(), activityFeedArrayList);
                    listView.setAdapter(meetupAdapter);
                } else{
                    activityFeedArrayList = new ArrayList();
                    Log.i(TAG, "activityFeedArrayList is empty");
                }
            }
            @Override
            public void onFailure(Call<ActivityFeed> call, Throwable t) {
                Log.e(TAG, "FML. Fail " + t.getMessage());
            }
        });
        return v;
    }
}
