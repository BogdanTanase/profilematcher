package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ProfileEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LevelMatcher implements ProfileMatcher{
    private final int minLevel;
    private final int maxLevel;

    @Override
    public boolean matches(ProfileEntity profile) {
        int profileLevel = profile.getLevel();
        return profileLevel >= minLevel && profileLevel <= maxLevel;
    }
}
