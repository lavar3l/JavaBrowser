package com.browser.javabrowser;

public interface IBrowsable {
    void navigateForward();
    void navigateBackward();
    void navigateURL(String url);
    void reloadPage();
}
