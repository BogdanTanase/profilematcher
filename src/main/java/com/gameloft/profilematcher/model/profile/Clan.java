package com.gameloft.profilematcher.model.profile;

import lombok.Builder;

@Builder(toBuilder = true)
public record Clan(
        String id,
        String name
) {
}
