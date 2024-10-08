package com.gameloft.profilematcher.service;

import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.model.profile.Profile;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface ProfileService {
    Profile getProfile(UUID profileId) throws IllegalArgumentException, NoSuchElementException;

    Profile getProfileAndApplyCampaign(UUID profileId) throws IllegalArgumentException, NoSuchElementException;

    Profile saveProfile(Profile profile) throws IllegalArgumentException, ElementAlreadyExistsException;

    void deleteProfile(UUID profileId) throws IllegalArgumentException;
}
