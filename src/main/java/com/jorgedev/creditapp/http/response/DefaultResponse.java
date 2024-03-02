package com.jorgedev.creditapp.http.response;

public class DefaultResponse {
    Boolean success;
    String message;
    Object body;

    public DefaultResponse(String message, Object body) {
        this.success = true;
        this.message = message;
        this.body = body;
    }

    public DefaultResponse() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
