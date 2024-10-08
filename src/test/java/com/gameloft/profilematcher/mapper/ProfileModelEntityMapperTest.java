package com.gameloft.profilematcher.mapper;

import com.gameloft.profilematcher.entity.DeviceEntity;
import com.gameloft.profilematcher.entity.ItemEntity;
import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.model.profile.Item;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.model.profile.ProfileRequest;
import com.gameloft.profilematcher.utils.MockDataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProfileModelEntityMapperTest {
    private Profile mockProfile;
    private ProfileEntity mockProfileEntity;

    @BeforeEach
    public void setUp() {
        // Generate mock data for profile and profileEntity before each test
        mockProfile = MockDataGenerator.createMockProfile(
                UUID.randomUUID(),
                "test_credential",
                OffsetDateTime.now().minusDays(10),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                500,
                50,
                10,
                OffsetDateTime.now().minusDays(1),
                Arrays.asList("Campaign_1", "Campaign_2"),
                MockDataGenerator.createMockDevices(),
                5,
                2000,
                1000,
                "US",
                "en",
                OffsetDateTime.now().minusYears(25),
                "female",
                MockDataGenerator.createMockInventory(),
                MockDataGenerator.createMockClan(),
                "CustomField_1"
        );

        List<DeviceEntity> devices = new ArrayList<>();
        for (Device d : mockProfile.devices()) {
            devices.add(DeviceModelEntityMapper.toEntity(d));
        }

        List<ItemEntity> inventoryEntityList = new ArrayList<>();
        for (Map.Entry<String, Integer> item : mockProfile.inventory().entrySet()) {
            inventoryEntityList.add(ItemEntity.builder()
                    .itemName(item.getKey())
                    .quantity(item.getValue())
                    .build());

            mockProfileEntity = ProfileEntity.builder()
                    .playerId(mockProfile.playerId())
                    .credential(mockProfile.credential())
                    .created(mockProfile.created())
                    .modified(mockProfile.modified())
                    .lastSession(mockProfile.lastSession())
                    .totalSpent(mockProfile.totalSpent())
                    .totalRefund(mockProfile.totalRefund())
                    .totalTransactions(mockProfile.totalTransactions())
                    .lastPurchase(mockProfile.lastPurchase())
                    .activeCampaigns(mockProfile.activeCampaigns())
                    .devices(devices)
                    .level(mockProfile.level())
                    .xp(mockProfile.xp())
                    .totalPlaytime(mockProfile.totalPlaytime())
                    .country(mockProfile.country())
                    .language(mockProfile.language())
                    .birthdate(mockProfile.birthdate())
                    .gender(mockProfile.gender())
                    .inventory(inventoryEntityList)
                    .clan(ClanModelEntityMapper.toEntity(mockProfile.clan()))
                    ._customField(mockProfile._customField())
                    .build();
        }
    }

    @Test
    public void testToEntity() {
        // When converting from model to entity
        ProfileEntity profileEntity = ProfileModelEntityMapper.toEntity(mockProfile);

        // Verify that the profileEntity fields match the mockProfile fields
        assertNotNull(profileEntity);
        assertEquals(mockProfile.playerId(), profileEntity.getPlayerId());
        assertEquals(mockProfile.credential(), profileEntity.getCredential());
        assertEquals(mockProfile.created(), profileEntity.getCreated());
        assertEquals(mockProfile.modified(), profileEntity.getModified());
        assertEquals(mockProfile.level(), profileEntity.getLevel());
        assertEquals(mockProfile.country(), profileEntity.getCountry());

        // Ensure the devices and inventory were mapped correctly
        assertEquals(mockProfile.devices().size(), profileEntity.getDevices().size());
        assertEquals(mockProfile.inventory().size(), profileEntity.getInventory().size());
        assertEquals(mockProfile.clan().name(), profileEntity.getClan().getName());
    }

    @Test
    public void testToEntity_NullInput() {
        // When passing null, expect null in response
        ProfileEntity profileEntity = ProfileModelEntityMapper.toEntity(null);
        assertNull(profileEntity);
    }

    @Test
    public void testToModel() {
        // When converting from entity to model
        Profile profile = ProfileModelEntityMapper.toModel(mockProfileEntity);

        // Verify that the profile fields match the mockProfileEntity fields
        assertNotNull(profile);
        assertEquals(mockProfileEntity.getPlayerId(), profile.playerId());
        assertEquals(mockProfileEntity.getCredential(), profile.credential());
        assertEquals(mockProfileEntity.getCreated(), profile.created());
        assertEquals(mockProfileEntity.getModified(), profile.modified());
        assertEquals(mockProfileEntity.getLevel(), profile.level());
        assertEquals(mockProfileEntity.getCountry(), profile.country());

        // Ensure the devices and inventory were mapped correctly
        assertEquals(mockProfileEntity.getDevices().size(), profile.devices().size());
        assertEquals(mockProfileEntity.getInventory().size(), profile.inventory().size());
        assertEquals(mockProfileEntity.getClan().getName(), profile.clan().name());
    }

    @Test
    public void testToModel_NullInput() {
        // When passing null, expect null in response
        ProfileEntity profileEntity = null;
        Profile profile = ProfileModelEntityMapper.toModel(profileEntity);
        assertNull(profile);
    }

    @Test
    public void testToModel_WithProfileRequest() {
        // Given a mock ProfileRequest
        ProfileRequest mockProfileRequest = Mockito.mock(ProfileRequest.class);
        Mockito.when(mockProfileRequest.player_id()).thenReturn(UUID.randomUUID());
        Mockito.when(mockProfileRequest.credential()).thenReturn("test_credential");
        Mockito.when(mockProfileRequest.created()).thenReturn(OffsetDateTime.now().minusDays(5));
        Mockito.when(mockProfileRequest.modified()).thenReturn(OffsetDateTime.now());
        Mockito.when(mockProfileRequest.last_session()).thenReturn(OffsetDateTime.now().minusDays(2));
        Mockito.when(mockProfileRequest.level()).thenReturn(5);
        Mockito.when(mockProfileRequest.country()).thenReturn("US");

        // When converting from ProfileRequest to Profile
        Profile profile = ProfileModelEntityMapper.toModel(mockProfileRequest);

        // Verify that the profile fields match the mockProfileRequest fields
        assertNotNull(profile);
        assertEquals(mockProfileRequest.player_id(), profile.playerId());
        assertEquals(mockProfileRequest.credential(), profile.credential());
        assertEquals(mockProfileRequest.created(), profile.created());
        assertEquals(mockProfileRequest.modified(), profile.modified());
        assertEquals(mockProfileRequest.level(), profile.level());
        assertEquals(mockProfileRequest.country(), profile.country());
    }
}