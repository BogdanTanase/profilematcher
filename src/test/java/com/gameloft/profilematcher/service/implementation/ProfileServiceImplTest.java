package com.gameloft.profilematcher.service.implementation;

import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.mapper.ProfileModelEntityMapper;
import com.gameloft.profilematcher.model.campaign.Campaign;
import com.gameloft.profilematcher.model.campaign.Level;
import com.gameloft.profilematcher.model.campaign.Matcher;
import com.gameloft.profilematcher.model.profile.Clan;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.repository.ProfileJpaRepository;
import com.gameloft.profilematcher.service.CampaignService;
import com.gameloft.profilematcher.utils.MockDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceImplTest {
    @Mock
    private ProfileJpaRepository profileJpaRepository;

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private ProfileServiceImpl profileServiceImpl;

    private ProfileEntity mockProfileEntity;
    private UUID playerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Device> devices = new ArrayList<>();
        devices.add(Device.builder()
                        .id(1)
                        .model("apple iphone 11")
                        .carrier("vodafone")
                        .firmware("123")
                .build());

        Clan clan = Clan.builder()
                .id("123456")
                .name("Hello world clan")
                .build();

        // Create a mock profile using the data you provided
        playerId = UUID.fromString("97983be2-98b7-11e7-90cf-082e5f28d836");
        Profile mockProfile = MockDataGenerator.createMockProfile(
                playerId,
                "apple_credential",
                OffsetDateTime.parse("2021-01-10T13:37:17Z"),
                OffsetDateTime.parse("2021-01-23T13:37:17Z"),
                OffsetDateTime.parse("2021-01-23T13:37:17Z"),
                400,
                0,
                5,
                OffsetDateTime.parse("2021-01-22T13:37:17Z"),
                new ArrayList<>(),
                devices,
                3,
                1000,
                144,
                "CA",
                "fr",
                OffsetDateTime.parse("2000-01-10T13:37:17Z"),
                "male",
                Map.of("cash", 123, "coins", 123, "item_1", 1, "item_34", 3, "item_55", 2),
                clan,
                "mycustom"
        );

        mockProfileEntity = ProfileModelEntityMapper.toEntity(mockProfile);

        // Setup the profile repository mock to return the mock profile entity
        when(profileJpaRepository.save(mockProfileEntity)).thenReturn(mockProfileEntity);
        when(profileJpaRepository.findByPlayerId(playerId)).thenReturn(Optional.of(mockProfileEntity));
    }

    @Test
    void testGetProfileAndApplyCampaign_MatchingCampaign() {
        // Create a matching campaign
        Campaign matchingCampaign = Campaign.builder()
                .id(UUID.randomUUID())
                .game("mygame")
                .name("mycampaign")
                .priority(10.5)
                .matchers(
                        new Matcher(
                                Level.builder().min(1).max(3).build(),
                                Map.of("country", List.of("US", "RO", "CA"), "items" , List.of("item_1")),
                                Map.of("items", List.of("item_4"))
                        )
                )
                .start_date(String.valueOf(OffsetDateTime.parse("2022-01-25T00:00:00Z")))
                .end_date(String.valueOf(OffsetDateTime.parse("2022-02-25T00:00:00Z")))
                .build();

        when(campaignService.getCampaign()).thenReturn(matchingCampaign);

        Profile updatedProfile = profileServiceImpl.getProfileAndApplyCampaign(playerId);

        assertNotNull(updatedProfile);
        assertTrue(updatedProfile.activeCampaigns().contains("mycampaign"));

        // Verify that the profile was saved with the new campaign
        verify(profileJpaRepository, times(1)).save(mockProfileEntity);
    }

    @Test
    void testGetProfileAndApplyCampaign_NonMatchingCampaign() {
        // Create a non-matching campaign
        Campaign nonMatchingCampaign = Campaign.builder()
                .name("Non-Matching Campaign")
                .matchers(
                        new Matcher(Level.builder().min(1).max(5).build(), Map.of("country", List.of("US", "RO")), Map.of("items", List.of("item_4")))
                )
                .build();

        when(campaignService.getCampaign()).thenReturn(nonMatchingCampaign);

        Profile updatedProfile = profileServiceImpl.getProfileAndApplyCampaign(playerId);

        assertNotNull(updatedProfile);
        assertFalse(updatedProfile.activeCampaigns().contains("Non-Matching Campaign"));

        // Verify that the profile was not saved with the new campaign
        verify(profileJpaRepository, times(1)).save(mockProfileEntity); // Check that save is called only once, with no changes
    }
}