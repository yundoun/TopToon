package com.example.toptoon;

public class CommonContentItem {
    private String imageUrl;
    private String title;
    private String author;

    public CommonContentItem(String imageUrl, String title, String author) {
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
        CommonContentItem that = (CommonContentItem) o;
        return imageUrl.equals(that.imageUrl) &&
                title.equals(that.title) &&
                author.equals(that.author);
    }
}
