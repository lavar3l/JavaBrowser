package com.browser.javabrowser.settings;

import com.browser.javabrowser.json.serializer.Serializer;
import com.browser.javabrowser.searchers.SearcherEnum;

/*
 * Class for storing, loading and saving browser settings
 */

public final class Settings {
    // Default settings
    private static final String defaultHomePage = "http://google.com";
    private static final SearcherEnum defaultSearcher = SearcherEnum.GOOGLE;

    // Current settings
    private String homePage;
    private SearcherEnum searcher;

    private Settings() {
        this.homePage = Settings.defaultHomePage;
        this.searcher = Settings.defaultSearcher;
    }

    private Settings(String homePage, SearcherEnum searcher) {
        this.homePage = homePage;
        this.searcher = searcher;
    }

    private static Settings instance = null;
    public static Settings getInstance() {
        if (Settings.instance == null)
            Settings.instance = new Settings();

        return Settings.instance;
    }

    public static void initialize(String filePath)
    {
        Serializer serializer = new Serializer();
        Settings.instance = serializer.deserialize(Settings.class, filePath, serializer.getDefaultGson());
    }

    public static void saveToFile(String filePath) {
        Serializer serializer = new Serializer();
        serializer.serialize(Settings.instance, filePath, serializer.getDefaultGson());
    }

    //region Getters

    public String getHomePage() {
        return homePage;
    }

    public SearcherEnum getSearcher() {
        return searcher;
    }

    //endregion

    //region Setters

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setSearcher(SearcherEnum searcher) {
        this.searcher = searcher;
    }

    //endregion
}