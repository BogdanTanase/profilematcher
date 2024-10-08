package com.gameloft.profilematcher.service.implementation;

import com.gameloft.profilematcher.entity.DeviceEntity;
import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.mapper.DeviceModelEntityMapper;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.repository.DeviceJpaRepository;
import com.gameloft.profilematcher.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceJpaRepository deviceJpaRepository;

    @Override
    public Device getDevice(Integer deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException("Can't get device by null id from db");
        }

        Optional<DeviceEntity> deviceEntity = deviceJpaRepository.findById(deviceId);
        if (deviceEntity.isEmpty()) {
            throw new NoSuchElementException(String.format("Device with id [%s] not found", deviceId));
        }

        return DeviceModelEntityMapper.toModel(deviceEntity.get());
    }

    @Override
    public Device createDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Can't add null device in db");
        }

        Optional<DeviceEntity> existingDeviceEntity = deviceJpaRepository.findById(device.id());
        if (existingDeviceEntity.isPresent()) {
            throw new ElementAlreadyExistsException(String.format("Device with {%s} already exists", device.id()));
        }

        return DeviceModelEntityMapper.toModel(deviceJpaRepository.save(DeviceModelEntityMapper.toEntity(device)));
    }

    @Override
    public void deleteDevice(Integer deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException("Can't delete null device in bd");
        }
        deviceJpaRepository.deleteById(deviceId);
    }
}
