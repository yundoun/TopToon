package com.example.toptoon;

public class CommonContentItem {
    String imageUrl;
    String title;
    String author;

    public CommonContentItem(String imageUrl, String title, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    public String getImageUrl() { return imageUrl; }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

