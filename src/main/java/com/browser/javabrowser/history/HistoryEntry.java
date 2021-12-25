package com.browser.javabrowser.history;

import javafx.scene.web.WebHistory;
import java.util.Date;

public class HistoryEntry {
    private String title;
    private String url;
    private Date date;
    private Integer tabId;

    public HistoryEntry(WebHistory.Entry entry, Integer tabId)
    {
        this.title = entry.getTitle();
        this.url = entry.getUrl();
        this.date = entry.getLastVisitedDate();
        this.tabId = tabId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public Date getDate() {
        return this.date;
    }

    public Integer getTabId() {
        return this.tabId;
    }
}
