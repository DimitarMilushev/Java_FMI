package com.company;

import com.company.device.AbstractSmartDevice;
import com.company.device.SmartCamera;
import com.company.device.SmartDevice;
import com.company.device.SmartLamp;
import com.company.hub.SmartCityHub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//<short name of device type>-<device name>-<unique number per device type>
        SmartCamera smrt = new  SmartCamera("Gosho", 5, LocalDateTime.of(2000, 10, 10, 3, 2));
        //id = CM-Gosho-0
        SmartCamera smrt1 = new  SmartCamera("qwqew", 252, LocalDateTime.of(2005, 10, 10, 3, 2));
        AbstractSmartDevice smrt3 = new SmartLamp("Sosho", 223, LocalDateTime.of(2010, 10, 10, 3, 2));
        AbstractSmartDevice smrt55 = new SmartLamp("asdad", 22, LocalDateTime.now());
        // id = LM-asdad-1

        SmartCityHub hub = new SmartCityHub();

        hub.register(smrt);
        hub.register(smrt1);
        hub.register(smrt3);
        hub.register(smrt55);

        /*
        System.out.println(hub.getDeviceById("LM-asdad-1").getName());
        hub.unregister(smrt55);

        System.out.println(hub.getDeviceById("LM-asdad-1").getName());
        */

        System.out.println(hub.getTopNDevicesByPowerConsumption(2).toString());

    }
}
