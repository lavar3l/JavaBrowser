package com.browser.javabrowser.settings;

public class SettingsSave {
    public String homePage;

    public void saveSettings() {
        this.homePage = Settings.homePage;
    }
}
