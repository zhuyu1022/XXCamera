package com.leqi.xxcamera.util;

import android.net.Uri;
import android.util.Patterns;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2015/12/23 14:20
 */

public class UrlBuilder {
    private String baseURL;
    private ArrayList<String> API = new ArrayList<>();
    private ArrayList<UrlQuery> urlQueries = new ArrayList<>();

    /*带服务器地址的*/
    public UrlBuilder(@NonNull String baseUrl, @NonNull String API) {
        this.baseURL = baseUrl;
        this.API.add(API);
    }


    /**
     * 在api中加入更多东西，如orders/id/xxx
     * boolean isDash
     * true: the dash is a dash
     * false: the dash is a character
     */
    public UrlBuilder addAPI(Object API, boolean isDash) {
        if (API != null) {
            String divider = "/";
            String addOnApi = String.valueOf(API);
            if (addOnApi.contains(divider) && isDash) {
                String[] addOnApis = addOnApi.split(divider);
                for (String addOn : addOnApis) {
                    addAPI(addOn);
                }
            } else {
                this.API.add(addOnApi);
            }
        }
        return this;
    }

    /**
     * 默认方法，将"/"视为一个字符
     */
    public UrlBuilder addAPI(Object API) {
        return addAPI(API, false);
    }

    //在builder中加入query
    public UrlBuilder addQuery(String key, Object value) {
        this.urlQueries.add(new UrlQuery(key, value));
        return this;
    }

    private void validate() {
        if (this.baseURL == null) {
            throw new BuilderException("Base URL must be set!");
        }
        //API已初始化不可能为NULL，判断是否为空
        if (this.API.isEmpty()) {
            throw new BuilderException("API must be set!");
        }
    }

    /**
     * 根据需要生成url
     */
    public String build() {
        validate();
        if (!Patterns.WEB_URL.matcher(this.baseURL).matches()) {
            return null;
        }
        //拼接URL的基本内容
        Uri uri = Uri.parse(baseURL);
        Uri.Builder builder = uri.buildUpon();
        Set<String> parameters = new HashSet<>(uri.getQueryParameterNames());
        for (String API : this.API) {
            builder.appendEncodedPath(API);
        }
        //对各种query进行处理
        builder.clearQuery();
        for (UrlQuery urlQuery : this.urlQueries) {
            String key = urlQuery.getKey();
            Object value = urlQuery.getValue();
            if (key != null && value != null) {
                parameters.remove(key);
                builder.appendQueryParameter(key, String.valueOf(value));
            }
        }
        for (String parameter : parameters) {
            for (String value : uri.getQueryParameters(parameter)) {
                builder.appendQueryParameter(parameter, value);
            }
        }
        return builder.build().toString();
    }

    private class UrlQuery {
        String key;
        Object value;

        private UrlQuery(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        private String getKey() {
            return key;
        }

        private Object getValue() {
            return value;
        }
    }

    public static class BuilderException extends RuntimeException {
        public BuilderException(String message) {
            super("Invalid request builder: " + message);
        }
    }
}

    
