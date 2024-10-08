package com.gameloft.profilematcher.model.profile;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Device(
        Integer id,
        String model,
        String carrier,
        String firmware
) {
}
