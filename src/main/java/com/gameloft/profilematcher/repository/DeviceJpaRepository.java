package com.gameloft.profilematcher.repository;

import com.gameloft.profilematcher.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, Integer> {
}
