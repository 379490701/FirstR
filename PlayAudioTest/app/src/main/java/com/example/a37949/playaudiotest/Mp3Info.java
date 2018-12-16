package com.example.a37949.playaudiotest;

public class Mp3Info {
    private long id;
    private String title;
    private String artist;
    private long duration;
    private long size;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public long getSize() {
        return size;
    }

    public long getId() {
        return id;
    }

    public long getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }
}
