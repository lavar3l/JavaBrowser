package com.browser.javabrowser.collectors.history;

import com.browser.javabrowser.collectors.IEntry;
import javafx.scene.web.WebHistory;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class HistoryEntry implements IEntry {
    private String title;
    private String url;
    private Date date;

    public HistoryEntry(WebHistory.Entry entry)
    {
        this.title = entry.getTitle();
        this.url = entry.getUrl();
        this.date = entry.getLastVisitedDate();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public String getDate() {
        return DateFormatUtils.format(this.date, "yyyy-MM-dd HH:mm:ss");
    }
}