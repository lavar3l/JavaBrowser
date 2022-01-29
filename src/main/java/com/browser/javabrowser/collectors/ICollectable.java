package com.browser.javabrowser.collectors;

import com.browser.javabrowser.BrowserController;

/*
 * Interface for browser components that can transmit collectable data
 * to designated collectors, such as history and bookmark collectors
 */

public interface ICollectable<T extends ICollector> {
    void setCollector(T collector);
    void setParent(BrowserController parent);
}