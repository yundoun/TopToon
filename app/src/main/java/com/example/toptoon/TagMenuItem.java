package com.example.toptoon;

import java.util.Objects;

public class TagMenuItem {
    private final String title;

    public TagMenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    // equals()와 hashCode()를 오버라이드 하는 이유
    // 메모리상에서 같은 위치인지 확인하는 것이 아니라 객체의 필드값이 같은지 비교해야함
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TagMenuItem menu = (TagMenuItem) obj;
        return Objects.equals(title, menu.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }


}
