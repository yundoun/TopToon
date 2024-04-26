package com.example.toptoon;

import java.util.Objects;

public class MainListItem {

    public MainListItem(String title, int imageResourceId, String latestEpisode, String views) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.latestEpisode = latestEpisode;
        this.views = views;
    }

    private String title;
    private int imageResourceId;
    private String latestEpisode;
    private String views;



}
