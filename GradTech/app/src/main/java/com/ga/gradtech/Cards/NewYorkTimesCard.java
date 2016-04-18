package com.ga.gradtech.Cards;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JamieAyer on 4/18/16.
 */
public class NewYorkTimesCard {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("abstract")
    private final String mSummary;

    @SerializedName("image")
    ImageView mImage;
    @SerializedName("caption")
    private final String mCaption;

    public NewYorkTimesCard(String summary, String title, String caption) {
        this.mSummary = summary;
        this.mTitle = title;
        this.mCaption = caption;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public ImageView getImage() {
        return mImage;
    }

    public void setImage(ImageView image) {
        this.mImage = image;
    }

    public String getCaption() {
        return mCaption;
    }
}
