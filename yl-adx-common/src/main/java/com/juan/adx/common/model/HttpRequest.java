package com.juan.adx.common.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.juan.adx.common.enums.HTTPMethod;


public class HttpRequest {

    public String url;
    public HTTPMethod method;
    public String body;
    public HashMap<String, String> headers;
    public HashMap<String, String> parameters;
    public int connectTimeout;
    public int requestTimeout;
    public Charset bodyCharset;

    public HttpRequest(final HTTPMethod method, final String url) {
        super();
        this.method = method;
        this.url = url;

        this.headers = new HashMap<String, String>();
        this.parameters = new HashMap<String, String>();
        this.bodyCharset = StandardCharsets.UTF_8;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public HttpRequest setBodyCharset(Charset charset) {
        this.bodyCharset = charset;
        return this;
    }

    public HttpRequest addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HttpRequest addQueryParameter(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public HttpRequest addQueryParameter(String name, int value) {
        parameters.put(name, String.valueOf(value));
        return this;
    }

    public HttpRequest addQueryParameter(String name, long value) {
        parameters.put(name, String.valueOf(value));
        return this;
    }

    public HttpRequest setConnectionTimeout(int connectionTimeout) {
        this.connectTimeout = connectionTimeout;
        return this;
    }

    public HttpRequest setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }
}