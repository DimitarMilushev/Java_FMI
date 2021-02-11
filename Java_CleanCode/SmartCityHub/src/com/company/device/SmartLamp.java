package com.company.device;

import com.company.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends AbstractSmartDevice{
    private static int smartLampNumber;

    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime, DeviceType.LAMP, smartLampNumber++);
    }

    @Override
    public int getDeviceNumber() {
        return smartLampNumber;
    }
}
