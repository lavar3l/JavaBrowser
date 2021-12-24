package com.browser.javabrowser.tools;

import static java.lang.Math.min;

public final class URLSanitizer {
    private static int maxTitleLength = 12;

    public static String getSanitizedURL(String url) {
        return url.replace("http://", "")
                  .replace("https://", "")
                  .replace("www.", "");
    }

    public static String getTabTitle(String url) {
        String title = URLSanitizer.getSanitizedURL(url);
        return title.substring(0, min(title.length(), URLSanitizer.maxTitleLength));
    }

    public static String getFullURL(String url)
    {
        if(url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }
}
