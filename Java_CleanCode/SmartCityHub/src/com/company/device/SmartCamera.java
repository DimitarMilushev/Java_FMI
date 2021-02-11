package com.company.device;

import com.company.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartCamera extends AbstractSmartDevice{

    private static int smartCameraNumber;

    public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime, DeviceType.CAMERA, smartCameraNumber++);
    }

    @Override
    public int getDeviceNumber() {
        return smartCameraNumber;
    }
}
