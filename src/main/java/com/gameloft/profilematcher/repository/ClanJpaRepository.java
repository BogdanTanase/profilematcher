package com.gameloft.profilematcher.repository;

import com.gameloft.profilematcher.entity.ClanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanJpaRepository extends JpaRepository<ClanEntity, Integer> {
}
