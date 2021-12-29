package com.browser.javabrowser.collectors;

import com.browser.javabrowser.BrowserController;

public interface ICollectable<T extends ICollector> {
    void setCollector(T collector);
    void setParent(BrowserController parent);
}