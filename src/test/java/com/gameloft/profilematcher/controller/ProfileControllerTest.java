package com.gameloft.profilematcher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.model.profile.ProfileRequest;
import com.gameloft.profilematcher.service.ProfileService;
import com.gameloft.profilematcher.utils.MockDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private Profile mockProfile;
    private ProfileRequest profileRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        UUID playerId = UUID.randomUUID();

        mockProfile = MockDataGenerator.createMockProfile(
                playerId,
                "mock_credential",
                OffsetDateTime.now().minusDays(10),
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
                MockDataGenerator.createMockInventory(),
                MockDataGenerator.createMockClan(),
                "mockCustomField"
        );

        profileRequest = MockDataGenerator.mapProfileToProfileRequest(mockProfile);
    }

    // Get Tests
    @Test
    public void testGetProfileAndApplyCampaign_Success() throws Exception {
        Mockito.when(profileService.getProfileAndApplyCampaign(any(UUID.class)))
                .thenReturn(mockProfile);

        UUID playerId = mockProfile.playerId();

        mockMvc.perform(get("/profile/get_client_config/{player_id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProfileAndApplyCampaign_ProfileNotFound() throws Exception {
        UUID playerId = UUID.randomUUID();

        Mockito.when(profileService.getProfileAndApplyCampaign(any(UUID.class)))
                .thenThrow(new NoSuchElementException(String.format("Profile with id [%s] not found", playerId)));

        mockMvc.perform(get("/profile/get_client_config/{player_id}", playerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Profile with id [%s] not found", playerId)));
    }

    @Test
    public void testGetProfileAndApplyCampaign_InvalidUUID() throws Exception {
        mockMvc.perform(get("/profile/get_client_config/{player_id}", "invalid-uuid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Create Tests
    @Test
    public void testCreateProfile_Success() throws Exception {
        Mockito.when(profileService.saveProfile(Mockito.any())).thenReturn(mockProfile);

        String jsonContent = objectMapper.writeValueAsString(profileRequest);

        mockMvc.perform(post("/profile/create_profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    // Delete Tests
    @Test
    public void testDeleteProfile_Success() throws Exception {
        UUID playerId = UUID.randomUUID();
        Mockito.doNothing().when(profileService).deleteProfile(playerId);

        mockMvc.perform(delete("/profile/delete_profile/{profile_id}", playerId))
                .andExpect(status().isNoContent());
    }
}
