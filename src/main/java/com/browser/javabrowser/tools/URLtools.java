package com.browser.javabrowser.tools;

import com.browser.javabrowser.searchers.SearcherFactory;
import com.browser.javabrowser.settings.Settings;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.Math.min;

public final class URLtools {
    private static final int MAX_TITLE_LENGTH = 12;

    public static String getSanitizedURL(String url) {
        return url.replace("http://", "")
                  .replace("https://", "")
                  .replace("www.", "");
    }

    public static String getTabTitle(String url) {
        String title = URLtools.getSanitizedURL(url);
        return title.substring(0, min(title.length(), URLtools.MAX_TITLE_LENGTH));
    }

    public static String getFullURL(String url)
    {
        if (url.startsWith("http://") || url.startsWith("https://")) return url;
        if (URLtools.isValidURL("http://" + url)) return "http://" + url;
        return SearcherFactory.create(Settings.searcher).toSearchUrl(url);
    }

    public static boolean isValidURL(String url)
    {
        try {
            new URL(url).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }

        String sanitizedUrl = url.replace("http://", "");
        return sanitizedUrl.contains(".") || sanitizedUrl.startsWith("localhost");
    }
}
