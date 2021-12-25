package com.browser.javabrowser.history;

import com.browser.javabrowser.tools.SerializationTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryCollector implements IArchivable {
    private List<HistoryEntry> entries;

    public HistoryCollector() {
        this.entries = new ArrayList<HistoryEntry>();
    }

    public HistoryCollector(String filePath) {
        this.entries = SerializationTools.deserialize(filePath);
        if(this.entries == null) this.entries = new ArrayList<HistoryEntry>();
    }

    public void saveToFile(String filePath) {
        SerializationTools.serialize(filePath, this.entries);
    }

    @Override
    public void archive(WebHistory.Entry entry, Integer tabId) {
        this.entries.add(new HistoryEntry(entry, tabId));
    }

    public ObservableList<HistoryEntry> getData()
    {
        return FXCollections.observableList(this.entries);
    }
}
