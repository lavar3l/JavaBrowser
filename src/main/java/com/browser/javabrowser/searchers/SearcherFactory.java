package com.browser.javabrowser.searchers;

public final class SearcherFactory {

    public static ISearcher create(SearcherEnum searcherEnum)
    {
        if (searcherEnum.getValue() == SearcherEnum.GOOGLE.getValue()) return new GoogleSearcher();
        if (searcherEnum.getValue() == SearcherEnum.BING.getValue()) return new BingSearcher();
        if (searcherEnum.getValue() == SearcherEnum.YAHOO.getValue()) return new YahooSearcher();
        return new DuckDuckGoSearcher();
    }
}
