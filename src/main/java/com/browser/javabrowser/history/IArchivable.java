package com.browser.javabrowser.history;

import javafx.scene.web.WebHistory;

public interface IArchivable {
    void archive(WebHistory.Entry entry, Integer Id);
}
