package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ProfileEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CampaignNameMatcher implements ProfileMatcher {
    private String campaignName;

    @Override
    public boolean matches(ProfileEntity profile) {
        return !profile.getActiveCampaigns().contains(campaignName);
    }
}
