package com.gameloft.profilematcher.model.error;

import lombok.Builder;

@Builder(toBuilder = true)
public record ErrorResponse(
        int status,
        String message
) {
}
