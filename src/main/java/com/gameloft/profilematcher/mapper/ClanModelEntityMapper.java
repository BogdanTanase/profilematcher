package com.gameloft.profilematcher.mapper;

import com.gameloft.profilematcher.entity.ClanEntity;
import com.gameloft.profilematcher.model.profile.Clan;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClanModelEntityMapper {
    public static ClanEntity toEntity(Clan model) {
        return ClanEntity.builder()
                .id(Integer.parseInt(model.id()))
                .name(model.name())
                .build();
    }

    public static Clan toModel(ClanEntity entity) {
        return Clan.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .build();
    }
}
