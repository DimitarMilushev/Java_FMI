package com.company.warehouse.exceptions;

public class CapacityExceededException extends Exception{
    public CapacityExceededException (String msg) {
        super(msg);
    }
}
