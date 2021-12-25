module com.browser.javabrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;
    requires com.google.gson;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.win10;

    opens com.browser.javabrowser to javafx.fxml;
    exports com.browser.javabrowser;
}