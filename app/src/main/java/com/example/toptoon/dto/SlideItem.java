package com.example.toptoon.dto;

public class SlideItem {
    private final String imageUrl;
    private final String linkUrl;

    public SlideItem(String imageUrl, String linkUrl) {
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }
}
