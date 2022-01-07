package com.browser.javabrowser.settings;

import com.browser.javabrowser.json.serializer.Serializer;
import com.browser.javabrowser.searchers.SearcherEnum;

public final class Settings {
    private static final String defaultHomePage = "http://google.com";
    private static final SearcherEnum defaultSearcher = SearcherEnum.GOOGLE;

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

    // getters
    public String getHomePage() {
        return homePage;
    }

    public SearcherEnum getSearcher() {
        return searcher;
    }
    // -- * --

    // setters
    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public void setSearcher(SearcherEnum searcher) {
        this.searcher = searcher;
    }
    // -- * --
}