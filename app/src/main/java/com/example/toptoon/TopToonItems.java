package com.example.toptoon;

import java.util.List;

public class TopToonItems {
    private List<SlideAd> slideAd;
    private List<Event> event;
    private List<TabItem> tab;


    // Getter and Setter

    public List<SlideAd> getSlideAd() {
        return slideAd;
    }

    public List<Event> getEvent() {
        return event;
    }

    public List<TabItem> getTab() {
        return tab;
    }


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

        public String getLatest_episode() {
            return latest_episode;
        }

        public String getViews() {
            return views;
        }

        public boolean isIs_new() {
            return is_new;
        }

        public boolean isIs_discounted() {
            return is_discounted;
        }

        public boolean isIs_exclusive() {
            return is_exclusive;
        }

        public boolean isWait_free() {
            return wait_free;
        }

        public boolean isRecently_updated() {
            return recently_updated;
        }

        public String getImage_url() {
            return image_url;
        }

        public TabItem(String title, String author, String latest_episode,
                       String views, boolean is_new, boolean is_discounted,
                       boolean is_exclusive, boolean wait_free, boolean recently_updated,
                       String image_url) {
            this.title = title;
            this.author = author;
            this.latest_episode = latest_episode;
            this.views = views;
            this.is_new = is_new;
            this.is_discounted = is_discounted;
            this.is_exclusive = is_exclusive;
            this.wait_free = wait_free;
            this.recently_updated = recently_updated;
            this.image_url = image_url;
        }

        private String title;
        private String author;
        private String latest_episode;
        private String views;
        private boolean is_new;
        private boolean is_discounted;
        private boolean is_exclusive;
        private boolean wait_free;
        private boolean recently_updated;
        private String image_url;


    }

}
