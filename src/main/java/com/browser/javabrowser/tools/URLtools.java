package com.browser.javabrowser.tools;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static java.lang.Math.min;

public final class URLtools {
    private static int maxTitleLength = 12;

    public static String getSanitizedURL(String url) {
        return url.replace("http://", "")
                  .replace("https://", "")
                  .replace("www.", "");
    }

    public static String getTabTitle(String url) {
        String title = URLtools.getSanitizedURL(url);
        return title.substring(0, min(title.length(), URLtools.maxTitleLength));
    }

    public static String getFullURL(String url)
    {
        if(url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }

    public static boolean isValidURL(String url)
    {
        try {
            URL u = new URL(url);
            u.toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
        return true;
    }
}
