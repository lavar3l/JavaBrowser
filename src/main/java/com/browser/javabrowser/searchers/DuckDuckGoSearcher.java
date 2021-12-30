package com.browser.javabrowser.searchers;

public class DuckDuckGoSearcher implements ISearcher {
    @Override
    public String toSearchUrl(String phrase) {
        return "https://duckduckgo.com/?q=" + phrase;
    }

    @Override
    public String getName() {
        return "DuckDuckGo";
    }

    @Override
    public SearcherEnum toEnum() {
        return SearcherEnum.DUCK_DUCK_GO;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
