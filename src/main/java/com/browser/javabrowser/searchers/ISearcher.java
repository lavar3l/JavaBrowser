package com.browser.javabrowser.searchers;

/*
 * Interface for search engine components
 */

public interface ISearcher {
    public String toSearchUrl(String phrase);
    public String getName();
    public SearcherEnum toEnum();
    public String toString();
}
