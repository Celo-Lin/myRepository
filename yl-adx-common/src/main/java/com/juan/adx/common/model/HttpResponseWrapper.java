package com.juan.adx.common.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class HttpResponseWrapper {

    public int statusCode;
    public String reason;
    public String body;
    private byte[] byteBody;
    public HashMap<String, String> headers;

    public HttpResponseWrapper() {
        this.headers = new HashMap<String, String>();
    }

    public HttpResponseWrapper(final int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpResponseWrapper(final int statusCode, final String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpResponseWrapper(final int statusCode, final String body, final String reason) {
        this.statusCode = statusCode;
        this.body = body;
        this.reason = reason;
    }
    
    
    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

}