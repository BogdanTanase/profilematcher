package com.gameloft.profilematcher.mapper;

import com.gameloft.profilematcher.entity.DeviceEntity;
import com.gameloft.profilematcher.model.profile.Device;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceModelEntityMapper {
    public static DeviceEntity toEntity(Device model) {
        return DeviceEntity.builder()
                .id(model.id())
                .model(model.model())
                .firmware(model.firmware())
                .carrier(model.carrier())
                .build();
    }

    public static Device toModel(DeviceEntity entity) {
        return Device.builder()
                .id(entity.getId())
                .model(entity.getModel())
                .firmware(entity.getFirmware())
                .carrier(entity.getCarrier())
                .build();
    }
}
