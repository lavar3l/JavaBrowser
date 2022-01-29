package com.browser.javabrowser.settings;

/*
 * Paths to browser files
 */

public final class Paths {
    private final static String settingsPath = "settings.json";
    private final static String historyPath = "history.json";
    private final static String bookmarksPath = "bookmarks.json";
    private final static String macroPath = "./macros";

    public static String getSettingsPath() {
        return Paths.settingsPath;
    }
    public static String getHistoryPath() {
        return Paths.historyPath;
    }
    public static String getBookmarksPath() {
        return Paths.bookmarksPath;
    }
    public static String getMacroPath() { return Paths.macroPath; }
}