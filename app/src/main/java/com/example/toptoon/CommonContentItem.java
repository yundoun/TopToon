package com.example.toptoon;

public class CommonContentItem {
    int imageResourceId;
    String title;
    String subTitle;

    public CommonContentItem(int imageResourceId, String text1, String text2) {
        this.imageResourceId = imageResourceId;
        this.title = text1;
        this.subTitle = text2;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}

