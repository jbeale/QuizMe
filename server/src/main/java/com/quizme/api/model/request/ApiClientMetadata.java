package com.quizme.api.model.request;

/**
 * Created by jbeale on 1/31/15.
 */
public class ApiClientMetadata {
    public String remoteIp;
    public String userAgent;
    public String remoteHost;

    public ApiClientMetadata(String remoteIp, String remoteHost, String userAgent) {
        this.remoteHost = remoteHost;
        this.remoteIp = remoteIp;
        this.userAgent = userAgent;
    }
}
