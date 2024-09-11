package com.crud.school.school_crud.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObject {
    private int status;
    private String message;
    private Object data;

    public ResponseObject(int status, String message, Object data) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
