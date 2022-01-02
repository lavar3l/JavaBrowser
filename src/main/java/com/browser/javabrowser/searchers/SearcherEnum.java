package com.browser.javabrowser.searchers;

public enum SearcherEnum {
    GOOGLE(0),
    BING(1),
    YAHOO(2),
    DUCK_DUCK_GO(3);

    private final int value;

    SearcherEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
