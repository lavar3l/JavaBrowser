package com.browser.javabrowser.collectors.bookmarks;

import com.browser.javabrowser.collectors.ICollector;
import com.browser.javabrowser.json.serializer.Serializer;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
 * Class responsible for collecting, loading and saving bookmark data
 */

public class BookmarksCollector implements ICollector<BookmarkEntry> {
    private List<BookmarkEntry> entries;

    public BookmarksCollector(String filePath) {
        this.entries = new ArrayList<BookmarkEntry>();

        // Load bookmarks from file
        Serializer serializer = new Serializer();
        Type type = new TypeToken<ArrayList<BookmarkEntry>>() {}.getType();
        List<BookmarkEntry> previousEntries = serializer.deserialize(type, filePath, serializer.getDefaultGson());

        // Initialize empty bookmark collector in case of file missing
        if(previousEntries != null) this.entries.addAll(previousEntries);
    }

    @Override
    public void saveToFile(String filePath) {
        Serializer serializer = new Serializer();
        serializer.serialize(this.entries, filePath, serializer.getDefaultGson());
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