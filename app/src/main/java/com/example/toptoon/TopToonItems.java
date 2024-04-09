package com.example.toptoon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopToonItems {
    private List<SlideAd> slideAd;
    private List<Event> event;
    private List<TabItem> tabRealTime;
    private List<TabItem> tabNew;
    private List<TabItem> tabSale;


    // Getter and Setter

    public List<SlideAd> getSlideAd() {
        return slideAd;
    }

    public List<Event> getEvent() {
        return event;
    }

    public List<TabItem> getTabRealTime() {
        return tabRealTime;
    }

//    public List<TabItem> getTabNew() {
//        return tabNew;
//    }
//
//    public List<TabItem> getTabSale() {
//        return tabSale;
//    }


    // SlideAd Class
    public static class SlideAd {

        private String image_url;

        public String getImage_url() {
            return image_url;
        }

    }

    public static class Event {

        private String image_url;

        public String getImage_url() {
            return image_url;
        }

    }

    public static class TabItem {

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

        public TabItem(String title, String author, String latestEpisode,
                       String views, boolean isNew, boolean isDiscounted,
                       boolean isExclusive, boolean wait_free, boolean recentlyUpdated,
                       String imageUrl) {
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
