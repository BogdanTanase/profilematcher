package com.gameloft.profilematcher.utils;

import com.gameloft.profilematcher.model.profile.*;

import java.time.OffsetDateTime;
import java.util.*;

public class MockDataGenerator {
    private static final Random RANDOM = new Random();

    public static Profile createMockProfile(UUID playerId,
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
                                            String customField) {
        return Profile.builder()
                .playerId(Optional.ofNullable(playerId).orElse(UUID.randomUUID()))
                .credential(Optional.ofNullable(credential).orElse("credential_" + UUID.randomUUID()))
                .created(Optional.ofNullable(created).orElse(OffsetDateTime.now().minusDays(RANDOM.nextInt(100))))
                .modified(Optional.ofNullable(modified).orElse(OffsetDateTime.now()))
                .lastSession(Optional.ofNullable(lastSession).orElse(OffsetDateTime.now().minusDays(RANDOM.nextInt(30))))
                .totalSpent(Optional.ofNullable(totalSpent).orElse(RANDOM.nextInt(1000)))
                .totalRefund(Optional.ofNullable(totalRefund).orElse(RANDOM.nextInt(100)))
                .totalTransactions(Optional.ofNullable(totalTransactions).orElse(RANDOM.nextInt(100)))
                .lastPurchase(Optional.ofNullable(lastPurchase).orElse(OffsetDateTime.now().minusDays(RANDOM.nextInt(30))))
                .activeCampaigns(Optional.ofNullable(activeCampaigns).orElse(createMockActiveCampaigns()))
                .devices(Optional.ofNullable(devices).orElse(createMockDevices()))
                .level(Optional.ofNullable(level).orElse(RANDOM.nextInt(1, 10)))
                .xp(Optional.ofNullable(xp).orElse(RANDOM.nextInt(0, 10000)))
                .totalPlaytime(Optional.ofNullable(totalPlaytime).orElse(RANDOM.nextInt(1, 1000)))
                .country(Optional.ofNullable(country).orElse("Country_" + RANDOM.nextInt(1, 10)))
                .language(Optional.ofNullable(language).orElse("Language_" + RANDOM.nextInt(1, 5)))
                .birthdate(Optional.ofNullable(birthdate).orElse(OffsetDateTime.now().minusYears(RANDOM.nextInt(18, 40))))
                .gender(Optional.ofNullable(gender).orElse(RANDOM.nextBoolean() ? "male" : "female"))
                .inventory(Optional.ofNullable(inventory).orElse(createMockInventory()))
                .clan(Optional.ofNullable(clan).orElse(createMockClan()))
                ._customField(Optional.ofNullable(customField).orElse("Custom field " + UUID.randomUUID()))
                .build();
    }

    public static ProfileRequest createMockProfileRequest(UUID playerId) {
        return new ProfileRequest(
                playerId,
                "mock_credential",
                OffsetDateTime.now().minusDays(1),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                1000,
                0,
                50,
                OffsetDateTime.now().minusDays(1),
                List.of("Campaign1"),
                MockDataGenerator.createMockDevices(),
                10,
                10000,
                2000,
                "US",
                "en",
                OffsetDateTime.now().minusYears(20),
                "male",
                new HashMap<>(),
                MockDataGenerator.createMockClan(),
                "mockCustomField"
        );
    }

    public static List<Device> createMockDevices() {
        List<Device> devices = new ArrayList<>();
        int deviceCount = RANDOM.nextInt(1, 4);
        for (int i = 0; i < deviceCount; i++) {
            devices.add(createMockDevice());
        }
        return devices;
    }

    public static Device createMockDevice() {
        return Device.builder()
                .id(RANDOM.nextInt(1, 1000))
                .model("DeviceModel_" + RANDOM.nextInt(1, 100))
                .carrier("Carrier_" + RANDOM.nextInt(1, 5))
                .firmware("Firmware_" + RANDOM.nextInt(1, 10))
                .build();
    }

    public static Map<String, Integer> createMockInventory() {
        Map<String, Integer> inventory = new HashMap<>();
        int itemCount = RANDOM.nextInt(1, 5);
        for (int i = 0; i < itemCount; i++) {
            inventory.put("Item_" + RANDOM.nextInt(1, 50), RANDOM.nextInt(1, 10));
        }
        return inventory;
    }

    public static List<String> createMockActiveCampaigns() {
        List<String> activeCampaigns = new ArrayList<>();
        int campaignCount = RANDOM.nextInt(1, 5);
        for (int i = 0; i < campaignCount; i++) {
            activeCampaigns.add("Campaign_" + UUID.randomUUID());
        }
        return activeCampaigns;
    }

    public static Clan createMockClan() {
        return Clan.builder()
                .id(String.valueOf(RANDOM.nextInt(1, 100)))
                .name("Clan_" + UUID.randomUUID())
                .build();
    }

    public static Item createMockItem() {
        return Item.builder()
                .id(RANDOM.nextInt(1, 100))
                .itemName("Item_" + RANDOM.nextInt(1, 100))
                .quantity(RANDOM.nextInt(1, 10))
                .build();
    }

    public static ProfileRequest mapProfileToProfileRequest(Profile mockProfile) {
        return new ProfileRequest(
                mockProfile.playerId(),
                mockProfile.credential(),
                mockProfile.created(),
                mockProfile.modified(),
                mockProfile.lastSession(),
                mockProfile.totalSpent(),
                mockProfile.totalRefund(),
                mockProfile.totalTransactions(),
                mockProfile.lastPurchase(),
                mockProfile.activeCampaigns(),
                mockProfile.devices(),
                mockProfile.level(),
                mockProfile.xp(),
                mockProfile.totalPlaytime(),
                mockProfile.country(),
                mockProfile.language(),
                mockProfile.birthdate(),
                mockProfile.gender(),
                mockProfile.inventory(),
                mockProfile.clan(),
                mockProfile._customField()
        );
    }
}
