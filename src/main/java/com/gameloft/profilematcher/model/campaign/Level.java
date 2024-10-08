package com.gameloft.profilematcher.model.campaign;

import lombok.Builder;

@Builder(toBuilder = true)
public record Level(
        Integer min,
        Integer max
) {
}
