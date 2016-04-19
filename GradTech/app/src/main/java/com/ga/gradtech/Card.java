package com.ga.gradtech;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JamieAyer on 4/18/16.
 */

public class Card extends RecyclerView.ViewHolder {

    private TextView mCompanyName;
    private TextView mLocation;
    private ImageView photoId;

    public TextView getmCompanyName() {
        return mCompanyName;
    }

    public TextView getmLocation() {
        return mLocation;
    }

    public ImageView getPhotoId() {
        return photoId;
    }

    public void setmCompanyName(TextView mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public void setmLocation(TextView mLocation) {
        this.mLocation = mLocation;
    }

    public void setPhotoId(ImageView photoId) {
        this.photoId = photoId;
    }

    public Card(View view) {
        super(view);
        mCompanyName = (TextView)view.findViewById(R.id.company_name);
        mLocation = (TextView)view.findViewById(R.id.company_location);
        photoId = (ImageView)view.findViewById(R.id.company_photo);
    }
}