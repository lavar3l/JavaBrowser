package com.browser.javabrowser.history;

import com.browser.javabrowser.tools.SerializationTools;
import javafx.scene.web.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class History implements IArchivable {
    private List<HistoryEntry> entries;

    public History() {
        this.entries = new ArrayList<HistoryEntry>();
    }

    public History(String filePath) {
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

    public void print()
    {
        for(HistoryEntry e: this.entries)
        {
            System.out.println(e.getTitle() + " | " + e.getUrl() + " | " + e.getDate());
        }
    }
}
