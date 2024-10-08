package com.gameloft.profilematcher.matcher;

import com.gameloft.profilematcher.entity.ItemEntity;
import com.gameloft.profilematcher.entity.ProfileEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExcludedItemMatcher implements ProfileMatcher{
    private final List<String> excludedItems;

    @Override
    public boolean matches(ProfileEntity profile) {
        List<String> profileItems = profile.getInventory().stream()
                .map(ItemEntity::getItemName)
                .toList();
        return excludedItems.stream().noneMatch(profileItems::contains);
    }
}
