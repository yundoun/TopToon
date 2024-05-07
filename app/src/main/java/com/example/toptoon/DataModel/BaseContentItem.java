package com.example.toptoon.DataModel;

import java.util.Objects;

public class BaseContentItem {

//equals(): 이 메소드는 두 객체의 내용이 동일한지 비교합니다. 각 필드가 같은지 확인하며, boolean과 String 타입의 필드 모두를 비교합니다.
//hashCode(): 객체의 해시코드를 반환합니다. 객체의 동등성을 판단하는데 사용되며, equals() 메소드에서 사용된 모든 필드를 기반으로 해시코드를 계산해야 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseContentItem that = (BaseContentItem) o;
        return isNew == that.isNew &&
                isDiscounted == that.isDiscounted &&
                isExclusive == that.isExclusive &&
                waitFree == that.waitFree &&
                recentlyUpdated == that.recentlyUpdated &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(latestEpisode, that.latestEpisode) &&
                Objects.equals(views, that.views) &&
                Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, latestEpisode, views, isNew, isDiscounted, isExclusive, waitFree, recentlyUpdated, imageUrl);
    }

    public BaseContentItem(String title, String author, String latestEpisode,
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
