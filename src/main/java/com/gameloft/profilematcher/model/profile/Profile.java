package com.gameloft.profilematcher.model.profile;

import lombok.Builder;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
public record Profile(
        UUID playerId,
        String credential,
        OffsetDateTime created,
        OffsetDateTime modified,
        OffsetDateTime lastSession,
        Integer totalSpent,
        Integer totalRefund,
        Integer totalTransactions,
        OffsetDateTime lastPurchase,
        List<String> activeCampaigns,
        List<Device> devices,
        Integer level,
        Integer xp,
        Integer totalPlaytime,
        String country,
        String language,
        OffsetDateTime birthdate,
        String gender,
        Map<String, Integer> inventory,
        Clan clan,
        String _customField
) {
}
