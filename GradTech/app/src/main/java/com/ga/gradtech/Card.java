package com.ga.gradtech;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JamieAyer on 4/18/16.
 */

public class Card {

    private String mCompanyName;
    private String mLocation;
    private ImageView photoId;

    public String getmCompanyName() {
        return mCompanyName;
    }

    public String getmLocation() {
        return mLocation;
    }

    public ImageView getPhotoId() {
        return photoId;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setPhotoId(ImageView photoId) {
        this.photoId = photoId;
    }

//    public Card(View view) {
//        super(view);
//        mCompanyName = (TextView)view.findViewById(R.id.company_name);
//        mLocation = (TextView)view.findViewById(R.id.company_location);
//        photoId = (ImageView)view.findViewById(R.id.company_photo);
//    }
}