package com.company.device;

import com.company.enums.DeviceType;

import java.time.LocalDateTime;

public abstract class AbstractSmartDevice implements SmartDevice{

    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;
    private DeviceType type;
    private String id;

    public AbstractSmartDevice (String name, double powerConsumption, LocalDateTime installationDateTime,
                                DeviceType type, int number)
    {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
        this.type = type;
        this.id = String.format("%s-%s-%d", type.getShortName(), name, number);
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPowerConsumption() {
        return this.powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return this.installationDateTime;
    }

    @Override
    public DeviceType getType() {
        return this.type;
    }

    public int getDeviceNumber() { return 0;}
}
