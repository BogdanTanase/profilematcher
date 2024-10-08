package com.gameloft.profilematcher.controller;

import com.gameloft.profilematcher.model.profile.Clan;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.service.ClanService;
import com.gameloft.profilematcher.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device/")
public class DeviceController {
    private final DeviceService deviceService;

    /**
     * Retrieves a device based on the device ID.
     *
     * @param deviceId the device's unique identifier
     * @return Device object
     */
    @GetMapping("/{deviceId}")
    public Device getDevice(@PathVariable Integer deviceId) {
        return deviceService.getDevice(deviceId);
    }

    /**
     * Creates a new device based on the provided device.
     *
     * @param device the device data
     * @return created Device object
     */
    @PostMapping("/")
    public Device createDevice(@RequestBody Device device) {
        return deviceService.createDevice(device);
    }

    /**
     * Updates an existing device based on the provided clan ID.
     *
     * @param clanId the clans' unique identifier
     * @return updated Device object
     */
    @DeleteMapping("/{clanId}")
    public void deleteDevice(@PathVariable Integer clanId) {
        deviceService.deleteDevice(clanId);
    }

}
