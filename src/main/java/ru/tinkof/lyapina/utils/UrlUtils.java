package ru.tinkof.lyapina.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class UrlUtils {

    private UrlUtils(){}

    private static URL createUrlNoParams(final String url) throws MalformedURLException {
        return new URL(url);
    }

    public static URL createUrlWithParams(final String url, final Map<String, String> params) throws
            MalformedURLException {
        if(params == null || params.size() == 0){
            return createUrlNoParams(url);
        }

        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append("?");
        for(Map.Entry entry : params.entrySet()){
            stringBuilder.append(entry.getKey());

            if(entry.getValue() != null){
                stringBuilder.append("=").append(entry.getValue());
            }

            stringBuilder.append("&");
        }

        return new URL(stringBuilder.toString());
    }
}
