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


import com.ga.gradtech.Cards.Meetup.MeetupAPIService;
import com.ga.gradtech.Cards.Meetup.MeetupAdapter;
import com.ga.gradtech.Cards.Meetup.Model.ActivityFeed;
import com.ga.gradtech.R;

import java.util.ArrayList;
import java.util.Arrays;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leisforkokomo on 4/20/16.
 */
public class MeetupResultsFragment extends Fragment{
    private final static String TAG = "MeetupResultsFragment";
    String accessToken;
    ListView listView;
    ArrayList activityFeedArrayList;
    MeetupAdapter meetupAdapter;
    Retrofit retrofit;
    MeetupAPIService service;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.meetup.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MeetupAPIService.class);
    }

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
//            meetupAdapter = new MeetupAdapter(getContext(), activityFeedArrayList);
//            listView.setAdapter(meetupAdapter);
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
