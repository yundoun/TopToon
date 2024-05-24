package com.example.toptoon.dto;

import java.util.Objects;

public class HorizontalContentItem {
    private final String imageUrl;
    private final String title;
    private final String author;
    private final String slug;

    public HorizontalContentItem(String imageUrl, String title, String author, String slug) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.slug = slug;
    }

    public String getImageUrl() { return imageUrl; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getSlug() { return slug; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorizontalContentItem that = (HorizontalContentItem) o;
        return Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, title, author, slug);
    }
}
