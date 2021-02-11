package com.company.hub;

public class DeviceAlreadyRegisteredException extends RuntimeException{
    public DeviceAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
