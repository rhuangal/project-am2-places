package com.rhuangal.myplaces2.data.network.entity;

/**
 * Created by rober on 23-Nov-17.
 */

public class ErrorResponse {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
