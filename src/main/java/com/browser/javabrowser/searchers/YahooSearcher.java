package com.browser.javabrowser.searchers;

public class YahooSearcher implements ISearcher {
    @Override
    public String toSearchUrl(String phrase) {
        return "https://search.yahoo.com/search?p=" + phrase;
    }

    @Override
    public String getName() {
        return "Yahoo";
    }

    @Override
    public SearcherEnum toEnum() {
        return SearcherEnum.YAHOO;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
