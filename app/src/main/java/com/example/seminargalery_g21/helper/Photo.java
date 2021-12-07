package com.example.seminargalery_g21.helper;

public class Photo {
    private String path;
    private int isReact;
    private int isRecycleBin;

    public Photo(String path, int isReact, int isRecycleBin) {
        this.path = path;
        this.isReact = isReact;
        this.isRecycleBin = isRecycleBin;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getReact() {
        return isReact;
    }

    public void setReact(int react) {
        isReact = react;
    }

    public int getRecycleBin() {
        return isRecycleBin;
    }

    public void setRecycleBin(int recycleBin) {
        isRecycleBin = recycleBin;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "path='" + path + '\'' +
                ", isReact=" + isReact +
                ", isRecycleBin=" + isRecycleBin +
                '}';
    }

}
