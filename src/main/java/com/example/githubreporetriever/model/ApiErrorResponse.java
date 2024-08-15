package com.example.githubreporetriever.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiErrorResponse {

    private int status;
    private String message;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
