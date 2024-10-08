package com.gameloft.profilematcher.repository;

import com.gameloft.profilematcher.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findByPlayerId(UUID playerId);
}
