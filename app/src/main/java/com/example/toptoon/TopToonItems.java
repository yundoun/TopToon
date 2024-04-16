package com.example.toptoon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopToonItems {


    @SerializedName("webtoons")
    private List<Webtoon> webtoons;

    @SerializedName("TabRealTime")
    private List<Integer> tabRealTime;

    @SerializedName("TabNew")
    private List<Integer> tabNew;

    @SerializedName("TabSale")
    private List<Integer> tabSale;

    @SerializedName("waitFree")
    private List<Integer> waitFree;

    @SerializedName("oneCoin")
    private List<Integer> oneCoin;

    // 광고 필드는 변경 없음
    private List<SlideAd> slideAd;
    private List<Event> event;
    private String headerAd;
    private String freeAd;
    private String sectionAd;


    // Getter and Setter
    public List<Webtoon> getWebtoons() { return webtoons; }
    public List<Integer> getTabRealTime() { return tabRealTime; }
    public List<Integer> getTabNew() { return tabNew; }
    public List<Integer> getTabSale() { return tabSale; }
    public List<Integer> getWaitFree() { return waitFree; }
    public List<Integer> getOneCoin() { return oneCoin; }
    public List<SlideAd> getSlideAd() { return slideAd; }

    public List<Event> getEvent() { return event; }

    public String getHeaderAd() { return headerAd; }

    public String getFreeAd() { return freeAd; }

    public String getSectionAd() { return sectionAd; }



    // Inner Classes

    public static class SlideAd {
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

    }

    public static class Event {

        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

    }

    public static class Webtoon {

        public int getId() {
            return id;
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

        public Webtoon(int id, String title, String author, String latestEpisode,
                       String views, boolean isNew, boolean isDiscounted,
                       boolean isExclusive, boolean wait_free, boolean recentlyUpdated,
                       String imageUrl) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.latestEpisode = latestEpisode;
            this.views = views;
            this.isNew = isNew;
            this.isDiscounted = isDiscounted;
            this.isExclusive = isExclusive;
            this.waitFree = wait_free;
            this.recentlyUpdated = recentlyUpdated;
            this.imageUrl = imageUrl;
        }

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("author")
        private String author;

        @SerializedName("latestEpisode")
        private String latestEpisode;

        @SerializedName("views")
        private String views;

        @SerializedName("isNew")
        private boolean isNew;

        @SerializedName("isDiscounted")
        private boolean isDiscounted;

        @SerializedName("isExclusive")
        private boolean isExclusive;

        @SerializedName("waitFree")
        private boolean waitFree;

        @SerializedName("recentlyUpdated")
        private boolean recentlyUpdated;

        @SerializedName("imageUrl")
        private String imageUrl;


    }

}
