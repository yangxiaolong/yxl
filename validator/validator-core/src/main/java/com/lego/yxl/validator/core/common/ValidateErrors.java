package com.lego.yxl.validator.core.common;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ValidateErrors {
    private List<Error> errors = new ArrayList<>();

    public void addError(String name, String code, String msg){
        Error error = new Error(name, code, msg);
        this.errors.add(error);
    }

    public boolean hasError() {
        return !this.errors.isEmpty();
    }

    @Value
    public static class Error {
        private String name;
        private String code;
        private String msg;
    }
}
