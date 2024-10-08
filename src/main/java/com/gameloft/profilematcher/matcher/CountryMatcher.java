package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ProfileEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CountryMatcher implements ProfileMatcher{
    private final List<String> allowedCountries;

    @Override
    public boolean matches(ProfileEntity profile) {
        return allowedCountries.contains(profile.getCountry());
    }
}
