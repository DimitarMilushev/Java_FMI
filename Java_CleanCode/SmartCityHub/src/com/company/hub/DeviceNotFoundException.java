package com.company.hub;

public class DeviceNotFoundException extends RuntimeException{

    public DeviceNotFoundException(String msg) {
        super(msg);
    }
}
