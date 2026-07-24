package com.pagepulse.model;

public class ErrorResponse {
    private String error;
    private String message;
    private String url;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message, String url) {
        this.error = error;
        this.message = message;
        this.url = url;
    }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
