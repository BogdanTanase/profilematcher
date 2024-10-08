package com.gameloft.profilematcher.service.implementation;

import com.gameloft.profilematcher.entity.ClanEntity;
import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.mapper.ClanModelEntityMapper;
import com.gameloft.profilematcher.model.profile.Clan;
import com.gameloft.profilematcher.repository.ClanJpaRepository;
import com.gameloft.profilematcher.service.ClanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClanServiceImpl implements ClanService {
    private final ClanJpaRepository clanService;

    @Override
    public Clan getClan(String clanId) {
        if (clanId == null) {
            throw new IllegalArgumentException("Can't get clan by null id from db");
        }

        Optional<ClanEntity> clanEntity = clanService.findById(Integer.valueOf(clanId));
        if (clanEntity.isEmpty()) {
            throw new NoSuchElementException(String.format("Clan with id [%s] not found", clanId));
        }

        return ClanModelEntityMapper.toModel(clanEntity.get());
    }

    @Override
    public Clan createClan(Clan clan) {
        if (clan == null) {
            throw new IllegalArgumentException("Can't create null clan in bd");
        }

        Optional<ClanEntity> existingClan = clanService.findById(Integer.valueOf(clan.id()));
        if (existingClan.isPresent()) {
            throw new ElementAlreadyExistsException(String.format("Clan with {%s} already exists", clan.id()));
        }

        return ClanModelEntityMapper.toModel(clanService.save(ClanModelEntityMapper.toEntity(clan)));
    }

    @Override
    public void deleteClan(String clanId) {
        if (clanId == null) {
            throw new IllegalArgumentException("Can't delete null clan in bd");
        }
        clanService.deleteById(Integer.valueOf(clanId));
    }
}
