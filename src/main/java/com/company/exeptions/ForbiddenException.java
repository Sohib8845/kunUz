package com.company.exeptions;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String meesage) {
        super("Forbidden exp");
    }
}
