package com.browser.javabrowser.searchers;

public class GoogleSearcher implements ISearcher {
    @Override
    public String toSearchUrl(String phrase) {
        return "https://google.com/search?q=" + phrase;
    }

    @Override
    public String getName() {
        return "Google";
    }

    @Override
    public SearcherEnum toEnum() {
        return SearcherEnum.GOOGLE;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
