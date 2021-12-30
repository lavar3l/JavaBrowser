package com.browser.javabrowser.searchers;

public final class SearcherFactory {

    public static ISearcher create(SearcherEnum searcherEnum)
    {
        return switch (searcherEnum) {
            case GOOGLE -> new GoogleSearcher();
            case BING -> new BingSearcher();
            case YAHOO -> new YahooSearcher();
            default -> new DuckDuckGoSearcher();
        };
    }
}
