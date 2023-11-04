package com.ozapp.foreignexchanger.data.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RestClientException extends RuntimeException {
    private int code;

    private String type;

    private String detail;

    public RestClientException( int code, String type, String detail) {
        super(detail);
    }

    @Override
    @JsonIgnore
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
