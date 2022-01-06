package com.browser.javabrowser.macro;
import com.browser.javabrowser.BrowserController;

public interface IMacro {
    @Override
    String toString();

    void execute(BrowserController controller);
}