package com.gameloft.profilematcher.model.profile;

import lombok.Builder;

@Builder(toBuilder = true)
public record Item (
        Integer id,
        String itemName,
        Integer quantity
) {
}
