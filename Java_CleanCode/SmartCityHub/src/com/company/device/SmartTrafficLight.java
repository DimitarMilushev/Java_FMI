package com.company.device;

import com.company.enums.DeviceType;

import java.time.LocalDateTime;

public class SmartTrafficLight extends AbstractSmartDevice{
    private static int smartTrafficLightNumber;

    public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime, DeviceType.TRAFFIC_LIGHT, smartTrafficLightNumber++);
    }

    @Override
    public int getDeviceNumber() {
        return smartTrafficLightNumber;
    }
}
