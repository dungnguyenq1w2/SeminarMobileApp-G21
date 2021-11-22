package com.example.seminargalery_g21.model;

public class Uploader {
    private String mName;
    private String mImageUrl;

    public Uploader(String name, String imageUrl) {
        mName = name;
        mImageUrl = imageUrl;

        if (name.trim().equals("")) {
            mName = "No name";
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }
}
