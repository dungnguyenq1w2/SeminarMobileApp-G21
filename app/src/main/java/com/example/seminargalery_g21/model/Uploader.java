package com.example.seminargalery_g21.model;

import com.google.firebase.Timestamp;

public class Uploader {
    private String mCaption;
    private String mImageUrl;
    private Timestamp mPubDate;

    public Uploader(String caption, String imageUrl, Timestamp pubDate) {
        mCaption = caption;
        mImageUrl = imageUrl;
        mPubDate = pubDate;
    }

    public String getCaption() {
        return mCaption;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Timestamp getmPubDate() {
        return mPubDate;
    }
}
