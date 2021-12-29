package com.browser.javabrowser.collectors.history;

import com.browser.javabrowser.collectors.ICollector;
import com.browser.javabrowser.tools.SerializationTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryCollector implements ICollector<HistoryEntry> {
    private List<HistoryEntry> entries;

    public HistoryCollector(String filePath) {
        this.entries = new ArrayList<HistoryEntry>();

        Class<HistoryEntry> type = HistoryEntry.class;
        List<HistoryEntry> previousEntries = SerializationTools.deserializeList(filePath, type);
        if(previousEntries != null) this.entries.addAll(previousEntries);
    }

    public void saveToFile(String filePath) {
        SerializationTools.serializeList(filePath, this.entries);
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