package com.example.toptoon;

public class TabContentItem {
    private int imageResourceId;
    private String rank;
    private String episode;
    private String views;
    private String title;

    // Constructor
    public TabContentItem(int imageResourceId, String rank, String episode, String views, String title) {
        this.imageResourceId = imageResourceId;
        this.rank = rank;
        this.episode = episode;
        this.views = views;
        this.title = title;
    }

    // Getters and Setters
    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    public String getRank() {
        return rank;
    }

    public String getEpisode() {
        return episode;
    }

    public String getViews() {
        return views;
    }

    public String getTitle() {
        return title;
    }

}
