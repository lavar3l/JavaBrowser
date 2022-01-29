package com.browser.javabrowser.collectors.history;

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
 * Class responsible for collecting, loading and saving history data
 */

public class HistoryCollector implements ICollector<HistoryEntry> {
    private List<HistoryEntry> entries;

    public HistoryCollector(String filePath) {
        this.entries = new ArrayList<HistoryEntry>();

        // Load history from file
        Serializer serializer = new Serializer();
        Type type = new TypeToken<ArrayList<HistoryEntry>>() {}.getType();
        List<HistoryEntry> previousEntries = serializer.deserialize(type, filePath, serializer.getDefaultGson());

        // Initialize empty history collector in case of file missing
        if(previousEntries != null) this.entries.addAll(previousEntries);
    }

    public void saveToFile(String filePath) {
        Serializer serializer = new Serializer();
        serializer.serialize(this.entries, filePath, serializer.getDefaultGson());
    }

    @Override
    public void archive(WebHistory.Entry entry) {
        this.entries.add(new HistoryEntry(entry));
    }

    @Override
    public void remove(HistoryEntry entry) {
        this.entries.remove(entry);
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public ObservableList<HistoryEntry> getData() {
        return FXCollections.observableList(this.entries);
    }
}