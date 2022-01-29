package com.browser.javabrowser;

/*
 * Interface for browser components that should implement browsing
 * navigation features
 */

public interface IBrowsable {
    void navigateForward();
    void navigateBackward();
    void navigateURL(String url);
    void reloadPage();
}
