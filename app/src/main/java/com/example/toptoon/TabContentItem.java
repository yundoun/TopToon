package com.example.toptoon;

public class TabContentItem {
    public TabContentItem(String title, String author, String latestEpisode,
                          String views, boolean isNew, boolean isDiscounted,
                          boolean isExclusive, boolean waitFree, boolean recentlyUpdated,
                          String imageUrl) {
        this.title = title;
        this.author = author;
        this.latestEpisode = latestEpisode;
        this.views = views;
        this.isNew = isNew;
        this.isDiscounted = isDiscounted;
        this.isExclusive = isExclusive;
        this.waitFree = waitFree;
        this.recentlyUpdated = recentlyUpdated;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLatestEpisode() {
        return latestEpisode;
    }

    public String getViews() {
        return views;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public boolean isWaitFree() {
        return waitFree;
    }

    public boolean isRecentlyUpdated() {
        return recentlyUpdated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private final String title;
    private final String author; // 추가된 필드
    private final String latestEpisode;
    private final String views;
    private final boolean isNew; // 추가된 필드
    private final boolean isDiscounted; // 추가된 필드
    private final boolean isExclusive; // 추가된 필드
    private final boolean waitFree; // 추가된 필드
    private final boolean recentlyUpdated; // 추가된 필드
    private final String imageUrl;


}
