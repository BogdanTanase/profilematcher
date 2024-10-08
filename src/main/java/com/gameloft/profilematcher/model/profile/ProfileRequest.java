package com.gameloft.profilematcher.model.profile;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import javax.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public record ProfileRequest (
        @NotNull(message = "Player ID is required")
        UUID player_id,
        String credential,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX") OffsetDateTime created,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX") OffsetDateTime modified,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX") OffsetDateTime last_session,
        int total_spent,
        int total_refund,
        int total_transactions,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX") OffsetDateTime last_purchase,
        List<String> active_campaigns,
        List<Device> devices,
        int level,
        int xp,
        int total_playtime,
        String country,
        String language,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssX") OffsetDateTime birthdate,
        String gender,
        Map<String, Integer> inventory,
        Clan clan,
        String _customField
){
}
