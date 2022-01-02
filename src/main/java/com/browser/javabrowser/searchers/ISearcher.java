package com.browser.javabrowser.searchers;

public interface ISearcher {
    public String toSearchUrl(String phrase);
    public String getName();
    public SearcherEnum toEnum();
    public String toString();
}
