package com.example.toptoon.DataModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiItems {

    // 필드
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
    private CustomKeyword customKeyword;
    private RecommendGenre recommendGenre;
    private Serial serial;

    // Getter and Setter
    public List<Webtoon> getWebtoons() {
        return webtoons;
    }

    public List<Integer> getTabRealTime() {
        return tabRealTime;
    }

    public List<Integer> getTabNew() {
        return tabNew;
    }

    public List<Integer> getTabSale() {
        return tabSale;
    }

    public List<Integer> getWaitFree() {
        return waitFree;
    }

    public List<Integer> getOneCoin() {
        return oneCoin;
    }

    public List<SlideAd> getSlideAd() {
        return slideAd;
    }

    public List<Event> getEvent() {
        return event;
    }

    public String getHeaderAd() {
        return headerAd;
    }

    public String getFreeAd() {
        return freeAd;
    }

    public String getSectionAd() {
        return sectionAd;
    }

    public CustomKeyword getCustomKeyword() {
        return customKeyword;
    }

    public RecommendGenre getRecommendGenre() {
        return recommendGenre;
    }

    public Serial getSerial() { return serial; }

    // 내부 클래스

    public static class Serial {
        private List<Integer> monday;
        private List<Integer> tuesday;
        private List<Integer> wednesday;
        private List<Integer> thursday;
        private List<Integer> friday;
        private List<Integer> saturday;
        private List<Integer> sunday;

        public List<Integer> getMonday() {
            return monday;
        }

        public List<Integer> getTuesday() {
            return tuesday;
        }

        public List<Integer> getWednesday() {
            return wednesday;
        }

        public List<Integer> getThursday() {
            return thursday;
        }

        public List<Integer> getFriday() {
            return friday;
        }

        public List<Integer> getSaturday() {
            return saturday;
        }

        public List<Integer> getSunday() {
            return sunday;
        }

        public List<Integer> getRemake() {
            return remake;
        }

        private List<Integer> remake;

    }

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

    public static class CustomKeyword {
        public List<Integer> getPopularWorks() {
            return popularWorks;
        }

        public List<Integer> getToptoonExclusive() {
            return toptoonExclusive;
        }

        public List<Integer> getDailyFree() {
            return dailyFree;
        }

        public List<Integer> getCompletelyFree() {
            return completelyFree;
        }

        public List<Integer> getHotNewWorks() {
            return hotNewWorks;
        }

        public List<Integer> getRemakes() {
            return remakes;
        }

        public List<Integer> getMillionViews() {
            return millionViews;
        }

        public List<Integer> getBingeWatching() {
            return bingeWatching;
        }

        private List<Integer> popularWorks;
        private List<Integer> toptoonExclusive;
        private List<Integer> dailyFree;
        private List<Integer> completelyFree;
        private List<Integer> hotNewWorks;
        private List<Integer> remakes;
        private List<Integer> millionViews;
        private List<Integer> bingeWatching;
    }

    public static class RecommendGenre {
        public List<Integer> getRomance() {
            return romance;
        }

        public List<Integer> getDrama() {
            return drama;
        }

        public List<Integer> getSchoolAction() {
            return schoolAction;
        }

        public List<Integer> getOmnibus() {
            return omnibus;
        }

        public List<Integer> getFantasySF() {
            return fantasySF;
        }

        public List<Integer> getHorrorThriller() {
            return horrorThriller;
        }

        public List<Integer> getComedy() {
            return comedy;
        }

        public List<Integer> getMartialArts() {
            return martialArts;
        }

        private List<Integer> romance;
        private List<Integer> drama;
        private List<Integer> schoolAction;
        private List<Integer> omnibus;
        private List<Integer> fantasySF;
        private List<Integer> horrorThriller;
        private List<Integer> comedy;
        private List<Integer> martialArts;
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
