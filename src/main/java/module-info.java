module com.browser.javabrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;
    requires com.google.gson;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.win10;

    requires org.apache.commons.lang3;

    opens com.browser.javabrowser to javafx.fxml;
    opens com.browser.javabrowser.collectors.history to javafx.base, com.google.gson;
    opens com.browser.javabrowser.settings to javafx.base, com.google.gson;
    opens com.browser.javabrowser.collectors.bookmarks to javafx.base, com.google.gson;
    exports com.browser.javabrowser;
    opens com.browser.javabrowser.collectors to com.google.gson, javafx.base;
    opens com.browser.javabrowser.searchers to com.google.gson;
}