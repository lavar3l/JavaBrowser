package com.browser.javabrowser.collectors;

import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory;

public interface ICollector<T extends IEntry> {
    void saveToFile(String filePath);
    void archive(WebHistory.Entry entry);
    void remove(T entry);
    void clear();
    ObservableList<T> getData();
}