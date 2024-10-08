package com.gameloft.profilematcher.model.campaign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Builder(toBuilder = true)
public record Campaign(
        UUID id,
        String game,
        String name,
        Double priority,
        Matcher matchers,
        @JsonProperty("start_date")
        String start_date,
        @JsonProperty("end_date")
        String end_date,
        Boolean enabled,
        @JsonProperty("last_updated")
        String last_updated
) {
    public OffsetDateTime getStartDateAsOffsetDateTime() {
        return OffsetDateTime.parse(start_date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public OffsetDateTime getEndDateAsOffsetDateTime() {
        return OffsetDateTime.parse(end_date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public OffsetDateTime getLastUpdatedAsOffsetDateTime() {
        return OffsetDateTime.parse(last_updated, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
