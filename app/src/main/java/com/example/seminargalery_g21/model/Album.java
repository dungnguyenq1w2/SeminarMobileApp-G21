package com.example.seminargalery_g21.model;

public class Album {
    private String albumName;
    private int albumImage;

    public Album(String albumName, int albumImage) {
        this.albumName = albumName;
        this.albumImage = albumImage;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(int albumImage) {
        this.albumImage = albumImage;
    }
}
