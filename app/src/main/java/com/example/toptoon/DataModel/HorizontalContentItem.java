package com.example.toptoon.DataModel;

public class HorizontalContentItem {
    private final String imageUrl;
    private final String title;
    private final String author;

    public HorizontalContentItem(String imageUrl, String title, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
    }

    public String getImageUrl() { return imageUrl; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorizontalContentItem that = (HorizontalContentItem) o;
        return imageUrl.equals(that.imageUrl) &&
                title.equals(that.title) &&
                author.equals(that.author);
    }
}
