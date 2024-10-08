package com.gameloft.profilematcher.service;

import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.model.profile.Device;

import java.util.NoSuchElementException;

public interface DeviceService {
    Device getDevice(Integer deviceId) throws IllegalArgumentException, NoSuchElementException;;

    Device createDevice(Device device) throws IllegalArgumentException, ElementAlreadyExistsException;;

    void deleteDevice(Integer deviceId) throws IllegalArgumentException;;
}
