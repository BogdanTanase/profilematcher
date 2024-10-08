package com.gameloft.profilematcher.controller;

import com.gameloft.profilematcher.mapper.ProfileModelEntityMapper;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.model.profile.ProfileRequest;
import com.gameloft.profilematcher.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/")
public class ProfileController {
    private final ProfileService profileService;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    /**
     * Gets the profile and applies any applicable campaign.
     *
     * @param playerId the player's unique identifier
     * @return Profile object
     */
    @GetMapping("/get_client_config/{player_id}")
    public ResponseEntity<Profile> getProfileAndApplyCampaign(@PathVariable("player_id") UUID playerId) {
        logger.info("Getting profile with ID: {} and applying campaign", playerId);
        Profile profile = profileService.getProfileAndApplyCampaign(playerId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Retrieves a profile based on the player ID.
     *
     * @param playerId the player's unique identifier
     * @return Profile object
     */
    @GetMapping("/get_profile/{player_id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("player_id") UUID playerId) {
        logger.info("Getting profile with ID: {}", playerId);
        Profile profile = profileService.getProfile(playerId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Creates a new profile based on the provided request.
     *
     * @param profileRequest the profile data
     * @return created Profile object
     */
    @PostMapping("/create_profile")
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        logger.info("Creating profile: {}", profileRequest);
        Profile profile = ProfileModelEntityMapper.toModel(profileRequest);
        Profile createdProfile = profileService.saveProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Deletes a profile based on the profile ID.
     *
     * @param profileId the unique identifier of the profile to delete
     * @return ResponseEntity indicating the operation's success
     */
    @DeleteMapping("/delete_profile/{profile_id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("profile_id") UUID profileId) {
        logger.info("Deleting profile with ID: {}", profileId);
        profileService.deleteProfile(profileId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}
