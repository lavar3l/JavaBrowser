package com.browser.javabrowser.settings;

import com.browser.javabrowser.searchers.SearcherEnum;

public class SettingsSave {
    public String homePage;
    public SearcherEnum searcher;

    public void saveSettings() {
        this.homePage = Settings.homePage;
        this.searcher = Settings.searcher;
    }
}
