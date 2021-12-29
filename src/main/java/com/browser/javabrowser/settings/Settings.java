package com.browser.javabrowser.settings;

import com.browser.javabrowser.tools.SerializationTools;

public final class Settings {
    private static final String homePage_default = "http://google.com";

    public static String homePage;

    public static void initialize(String filePath)
    {
        Class<SettingsSave> type = SettingsSave.class;
        SettingsSave save = SerializationTools.deserialize(filePath, type);
        if(save == null) Settings.restoreDefaults();
        else Settings.restoreSave(save);
    }

    public static void saveToFile(String filePath) {
        SettingsSave save = new SettingsSave();
        save.saveSettings();
        SerializationTools.serialize(filePath, save);
    }

    public static void restoreDefaults() {
        Settings.homePage = Settings.homePage_default;
    }

    private static void restoreSave(SettingsSave save) {
        Settings.homePage = save.homePage;
    }
}