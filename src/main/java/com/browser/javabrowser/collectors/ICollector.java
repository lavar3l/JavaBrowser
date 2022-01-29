package com.browser.javabrowser.collectors;

import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

/*
 * Interface for collector components that can collect, save and restore
 * collectable entries such as history and bookmarks
 */

public interface ICollector<T extends IEntry> {
    void saveToFile(String filePath);
    void archive(WebHistory.Entry entry);
    void remove(T entry);
    void clear();
    ObservableList<T> getData();
}