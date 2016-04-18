package com.ga.gradtech;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JamieAyer on 4/18/16.
 */

public class Card {

    String mCompanyName;
    String mLocation;
    int photoId;

    public String getmCompanyName() {
        return mCompanyName;
    }

    public String getmLocation() {
        return mLocation;
    }

    public int getPhotoId() {
        return photoId;
    }

    Card(String name, String location, int photoId) {
        this.mCompanyName = name;
        this.mLocation = location;
        this.photoId = photoId;


    }
}