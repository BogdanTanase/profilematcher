package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.model.campaign.Campaign;

import java.util.ArrayList;
import java.util.List;

public class CampaignMatcher {
    private final List<ProfileMatcher> matchers;

    public CampaignMatcher(List<ProfileMatcher> matchers) {
        this.matchers = matchers;
    }

    public boolean applies(ProfileEntity profile) {
        return matchers.stream().allMatch(matcher -> matcher.matches(profile));
    }

    public static CampaignMatcher fromCampaign(Campaign campaign) {
        List<ProfileMatcher> matchers = new ArrayList<>();

        // Level Matcher
        if (campaign.matchers().level() != null) {
            matchers.add(new LevelMatcher(campaign.matchers().level().min(), campaign.matchers().level().max()));
        }

        // Country Matcher
        if (campaign.matchers().has().containsKey("country")) {
            matchers.add(new CountryMatcher(campaign.matchers().has().get("country")));
        }

        // Item Matcher
        if (campaign.matchers().has().containsKey("items")) {
            matchers.add(new ItemMatcher(campaign.matchers().has().get("items")));
        }

        // Excluded Item Matcher
        if (campaign.matchers().doesNotHave().containsKey("items")) {
            matchers.add(new ExcludedItemMatcher(campaign.matchers().doesNotHave().get("items")));
        }

        matchers.add(new CampaignNameMatcher(campaign.name()));

        return new CampaignMatcher(matchers);
    }
}
