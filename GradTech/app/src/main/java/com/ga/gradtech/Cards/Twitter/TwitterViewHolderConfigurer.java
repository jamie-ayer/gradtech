package com.ga.gradtech.Cards.Twitter;

import android.app.Activity;

/**
 * Created by JacobDexter-Milling on 4/21/16.
 */
public class TwitterViewHolderConfigurer {

    TwitterViewHolder vh2;
    int position;
    Activity mainActivity;

    public TwitterViewHolderConfigurer (TwitterViewHolder vh2, int position, Activity mainActivity){
        this.vh2 = vh2;
        this.position = position;
        this.mainActivity = mainActivity;
    }

}
