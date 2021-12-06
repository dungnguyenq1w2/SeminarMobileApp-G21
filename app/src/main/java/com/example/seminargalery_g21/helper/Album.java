package com.example.seminargalery_g21.helper;

import java.util.ArrayList;

public class Album {
    private int id;
    private String albumName;
    private String albumImage;
    private ArrayList<Image> albumPhotosUri;

    public Album(String albumName, String albumImage, ArrayList<Image> albumPhotosUri ) {
        this.albumName = albumName;
        this.albumImage = albumImage;
        this.albumPhotosUri = albumPhotosUri;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public ArrayList<Image>  getAlbumPhotosUri() {
        if ( albumPhotosUri == null ) {
            albumPhotosUri = new ArrayList<>();
        }
        return albumPhotosUri;
    }

    public void setAlbumPhotosUri(ArrayList<Image> albumPhotosUri) {
        this.albumPhotosUri = albumPhotosUri;
    }
}
