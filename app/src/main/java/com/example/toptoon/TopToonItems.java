package com.example.toptoon;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopToonItems {
    private List<Webtoon> webtoons;
    private List<SlideAd> slideAdd;

    // Getter and Setter
    public List<Webtoon> getWebtoons() {
        return webtoons;
    }

    public void setWebtoons(List<Webtoon> webtoons) {
        this.webtoons = webtoons;
    }

    public List<SlideAd> getSlideAdd() {
        return slideAdd;
    }

    public void setSlideAdd(List<SlideAd> slideAdd) {
        this.slideAdd = slideAdd;
    }

    // Webtoon Class
    public static class Webtoon {
        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getGenre() {
            return genre;
        }

        public int getLatest_episode() {
            return latest_episode;
        }

        public int getViews() {
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

        public boolean isBecomes_free_after_some_time() {
            return becomes_free_after_some_time;
        }

        public boolean isRecently_updated() {
            return recently_updated;
        }

        public String getImage_url() {
            return image_url;
        }

        private String title;
        private String author;
        private String genre;
        private int latest_episode;
        private int views;
        private boolean is_new;
        private boolean is_discounted;
        private boolean is_exclusive;
        private boolean becomes_free_after_some_time;
        private boolean recently_updated;
        private String image_url;


    }

    // SlideAd Class
    public static class SlideAd {

        private String image_url;

        public String getImage_url() {
            return image_url;
        }

    }
}
