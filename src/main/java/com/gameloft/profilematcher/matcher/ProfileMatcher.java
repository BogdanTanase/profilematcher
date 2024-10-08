package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ProfileEntity;

public interface ProfileMatcher {
    boolean matches(ProfileEntity profile);
}
