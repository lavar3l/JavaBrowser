package com.browser.javabrowser.settings;

public final class Paths {
    private final static String settingsPath = "settings.json";
    private final static String historyPath = "history.json";
    private final static String bookmarksPath = "bookmarks.json";

    public static String getSettingsPath() {
        return Paths.settingsPath;
    }

    public static String getHistoryPath() {
        return Paths.historyPath;
    }

    public static String getBookmarksPath() {
        return Paths.bookmarksPath;
    }
}