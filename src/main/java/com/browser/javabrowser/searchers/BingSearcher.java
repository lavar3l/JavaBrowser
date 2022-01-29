package com.browser.javabrowser.searchers;

/*
 * Bing search engine wrapper
 */

public class BingSearcher implements ISearcher {
    @Override
    public String toSearchUrl(String phrase) {
        return "https://bing.com/search?q=" + phrase;
    }

    @Override
    public String getName() {
        return "Bing";
    }

    @Override
    public SearcherEnum toEnum() {
        return SearcherEnum.BING;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
