package com.browser.javabrowser.collectors.bookmarks;

import com.browser.javabrowser.collectors.ICollector;
import com.browser.javabrowser.tools.SerializationTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class BookmarksCollector implements ICollector<BookmarkEntry> {
    private List<BookmarkEntry> entries;

    public BookmarksCollector(String filePath) {
        this.entries = new ArrayList<BookmarkEntry>();

        Class<BookmarkEntry> type = BookmarkEntry.class;
        List<BookmarkEntry> previousEntries = SerializationTools.deserializeList(filePath, type);
        if(previousEntries != null) this.entries.addAll(previousEntries);
    }

    @Override
    public void saveToFile(String filePath) {
        SerializationTools.serializeList(filePath, this.entries);
    }

    @Override
    public void archive(WebHistory.Entry entry) {
        this.entries.add(new BookmarkEntry(entry));
    }

    @Override
    public void remove(BookmarkEntry entry) {
        this.entries.remove(entry);
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public ObservableList<BookmarkEntry> getData() {
        return FXCollections.observableList(this.entries);
    }
}