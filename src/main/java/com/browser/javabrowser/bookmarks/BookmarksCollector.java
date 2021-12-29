package com.browser.javabrowser.bookmarks;

import com.browser.javabrowser.history.HistoryEntry;
import com.browser.javabrowser.history.IArchivable;
import com.browser.javabrowser.tools.SerializationTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class BookmarksCollector implements IArchivable {
    private List<BookmarkEntry> entries;

    public BookmarksCollector() {
        this.entries = new ArrayList<BookmarkEntry>();
    }

    public BookmarksCollector(String filePath) {
        this.entries = new ArrayList<BookmarkEntry>();

        Class<BookmarkEntry> type = BookmarkEntry.class;
        List<BookmarkEntry> previousEntries = SerializationTools.deserializeList(filePath, type);
        if(previousEntries != null) this.entries.addAll(previousEntries);
    }

    public void saveToFile(String filePath) {
        SerializationTools.serializeList(filePath, this.entries);
    }

    @Override
    public void archive(WebHistory.Entry entry, Integer tabId) {
        this.entries.add(new BookmarkEntry(entry));
    }

    public void remove(BookmarkEntry entry) {
        this.entries.remove(entry);
    }

    public ObservableList<BookmarkEntry> getData() {
        return FXCollections.observableList(this.entries);
    }

    public void clear() {
        this.entries.clear();
    }
}
