package com.company.hub;

import com.company.device.SmartCamera;
import com.company.device.SmartDevice;
import com.company.enums.DeviceType;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class SmartCityHub {

    private final String DEVICE_CANNOT_BE_NULL = "Device cannot be null!";
    private final String DEVICE_NOT_FOUND = "No such device found!";

    private final Map<String, SmartDevice> devices;
    private final Map<DeviceType, Integer> registeredTypes;


    public SmartCityHub() {

        //Set type is implicitly set to SmartDevice
        this.devices = new LinkedHashMap<>();
        this.registeredTypes = new EnumMap<>(DeviceType.class);
    }


    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {

        if(device == null) {
            throw new IllegalArgumentException(this.DEVICE_CANNOT_BE_NULL);
        }

        if(IsDeviceInList(device)) {
            throw new DeviceAlreadyRegisteredException(String.format("Device with ID %s is already registered!", device.getId()));
        }

        this.devices.put(device.getId(), device);

        DeviceType type = device.getType();
        int count = this.registeredTypes.getOrDefault(type, 0);

        this.registeredTypes.put(type, count);
        //throw new UnsupportedOperationException();
    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {

        if(device == null) {
            throw new IllegalArgumentException(this.DEVICE_CANNOT_BE_NULL);
        }

        if(!IsDeviceInList(device)) {
            throw new DeviceNotFoundException(this.DEVICE_NOT_FOUND);
        }

        this.devices.remove(device);
        this.registeredTypes.put(device.getType(), this.registeredTypes.get(device.getType()) - 1);
        //throw new UnsupportedOperationException();
    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if(id == null) {
            throw new IllegalArgumentException("Input ID is null!");
        }

        SmartDevice device = this.devices.get(id);

        if(device == null) {
            throw new DeviceNotFoundException(String.format("Device with ID %s is not found.", id));
        }
        else {
            return device;
        }
    }

    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if(type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }

        return this.registeredTypes.getOrDefault(type,0);
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     *
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if(n < 1) {
            throw new IllegalArgumentException("Number cannot be less than 1.");
        }

        List<SmartDevice> sortedList = new ArrayList<>(this.devices.values());

        Collections.sort(sortedList, new Comparator<SmartDevice>() {
            @Override
            public int compare(SmartDevice o1, SmartDevice o2) {
                return getDeviceOverallPowerConsumption(o1) - getDeviceOverallPowerConsumption(o2);
            }
        });

        Iterator<SmartDevice> it = sortedList.iterator();

        List<String> topFiveConsumers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            topFiveConsumers.add(it.next().getId());
        }

        return topFiveConsumers;
    }

    private int getDeviceOverallPowerConsumption(SmartDevice device) {
        double timeBetween = Duration.between(device.getInstallationDateTime(), LocalDateTime.now()).toHours();

        return (int) (timeBetween * device.getPowerConsumption());
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     *
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if(n < 1) {
            throw new IllegalArgumentException("Number cannot be negative.");
        }

        if(n > this.devices.size()) {
            throw new IllegalArgumentException("Number cannot be greater than the devices.");
        }


        return new ArrayList<>(this.devices.values()).subList(0, n);
        //throw new UnsupportedOperationException();
    }

    private Boolean IsDeviceInList(SmartDevice device) {
        return this.devices.containsValue(device);
    }
    private Boolean IsDeviceInList(String id) {
        return this.devices.containsKey(id);
    }
}
