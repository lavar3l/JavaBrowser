package com.browser.javabrowser.bookmarks;

import javafx.scene.web.WebHistory;

public class BookmarkEntry {
    private String title;
    private String url;

    public BookmarkEntry(WebHistory.Entry entry)
    {
        this.title = entry.getTitle();
        this.url = entry.getUrl();
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }
}
