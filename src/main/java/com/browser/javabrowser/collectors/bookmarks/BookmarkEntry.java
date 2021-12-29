package com.browser.javabrowser.collectors.bookmarks;

import com.browser.javabrowser.collectors.IEntry;
import javafx.scene.web.WebHistory;

public class BookmarkEntry implements IEntry {
    private String title;
    private String url;

    public BookmarkEntry(WebHistory.Entry entry)
    {
        this.title = entry.getTitle();
        this.url = entry.getUrl();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}