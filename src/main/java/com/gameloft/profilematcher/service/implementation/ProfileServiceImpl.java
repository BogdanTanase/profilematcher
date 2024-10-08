package com.gameloft.profilematcher.service.implementation;

import com.gameloft.profilematcher.entity.ClanEntity;
import com.gameloft.profilematcher.entity.DeviceEntity;
import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.mapper.ClanModelEntityMapper;
import com.gameloft.profilematcher.mapper.DeviceModelEntityMapper;
import com.gameloft.profilematcher.mapper.ProfileModelEntityMapper;
import com.gameloft.profilematcher.matcher.CampaignMatcher;
import com.gameloft.profilematcher.model.campaign.Campaign;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.repository.ClanJpaRepository;
import com.gameloft.profilematcher.repository.DeviceJpaRepository;
import com.gameloft.profilematcher.repository.ProfileJpaRepository;
import com.gameloft.profilematcher.service.CampaignService;
import com.gameloft.profilematcher.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileJpaRepository profileJpaRepository;
    private final DeviceJpaRepository deviceJpaRepository;
    private final ClanJpaRepository clanJpaRepository;
    private final CampaignService campaignService;

    @Override
    public Profile getProfile(UUID profileId) throws NoSuchElementException {
        if (profileId == null) {
            throw new IllegalArgumentException("Can't get profile by null id from db");
        }

        ProfileEntity profileEntity = profileJpaRepository.findByPlayerId(profileId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Profile with id [%s] not found", profileId)));

        return ProfileModelEntityMapper.toModel(profileEntity);
    }

    @Override
    public Profile getProfileAndApplyCampaign(UUID profileId) {
        if (profileId == null) {
            throw new IllegalArgumentException("Can't get profile by null id from db");
        }

        ProfileEntity profileEntity = profileJpaRepository.findByPlayerId(profileId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Profile with id [%s] not found", profileId)));

        Campaign campaign = campaignService.getCampaign();

        if (doesCampaignApplyToProfile(campaign, profileEntity)) {
            profileEntity.getActiveCampaigns().add(campaign.name());
        }

        ProfileEntity savedProfileEntity = profileJpaRepository.save(profileEntity);

        return ProfileModelEntityMapper.toModel(savedProfileEntity);
    }

    @Override
    public Profile saveProfile(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Can't save null profile in db");
        }

        Optional<ProfileEntity> existingProfileEntity = profileJpaRepository.findByPlayerId(profile.playerId());
        if (existingProfileEntity.isPresent()) {
            throw new ElementAlreadyExistsException(String.format("Profile with {%s} already exists", profile.playerId().toString()));
        }

        ProfileEntity profileEntity = prepareProfileEntity(profile);
        return ProfileModelEntityMapper.toModel(profileJpaRepository.save(profileEntity));
    }

    @Override
    public void deleteProfile(UUID profileId) {
        if (profileId == null) {
            throw new IllegalArgumentException("Can't delete null profile from db");
        }
        profileJpaRepository.deleteById(profileId);
    }

    private ProfileEntity prepareProfileEntity(Profile profile) {
        ProfileEntity profileEntity = ProfileModelEntityMapper.toEntity(profile);
        if (profile.playerId() != null) {
            profileEntity.setId(profile.playerId());
        }

        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        for (Device device : profile.devices()) {
            Optional<DeviceEntity> deviceEntity = deviceJpaRepository.findById(device.id());

            if (deviceEntity.isEmpty()) {
                DeviceEntity newDevice = deviceJpaRepository.save(DeviceModelEntityMapper.toEntity(device));
                deviceEntityList.add(newDevice);
            } else {
                deviceEntityList.add(deviceEntity.get());
            }
        }
        profileEntity.setDevices(deviceEntityList);

        Optional<ClanEntity> clanEntity = clanJpaRepository.findById(Integer.valueOf(profile.clan().id()));
        if (clanEntity.isEmpty()) {
            ClanEntity newClan = clanJpaRepository.save(ClanModelEntityMapper.toEntity(profile.clan()));
            profileEntity.setClan(newClan);
        } else {
            profileEntity.setClan(clanEntity.get());
        }

        return profileEntity;
    }

    public boolean doesCampaignApplyToProfile(Campaign campaign, ProfileEntity profile) {
        CampaignMatcher matcher = CampaignMatcher.fromCampaign(campaign);
        return matcher.applies(profile);
    }
}

