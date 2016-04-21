package com.ga.gradtech.Cards.Meetup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.ga.gradtech.Cards.Meetup.MeetupAPIService;
import com.ga.gradtech.Cards.Meetup.Model.ActivityFeed;

import java.util.ArrayList;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the token from the main activity!

        //may need to remove / in base URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.meetup.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MeetupAPIService service = retrofit.create(MeetupAPIService.class);
        Call<ActivityFeed> activityFeedCall = service.listFeed("Bearer " + tokenFromLogin, 94014, "android", 34, 6);
        activityFeedCall.enqueue(new Callback<ActivityFeed>() {
            @Override
            public void onResponse(Call<ActivityFeed> call, Response<ActivityFeed> response) {
                Log.e(TAG, "Thank Jesus Christ, it works! The array has a length of " + response.body().getResults().length);
            }

            @Override
            public void onFailure(Call<ActivityFeed> call, Throwable t) {
                Log.e(TAG, "FML. Fail " + t.getMessage());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
