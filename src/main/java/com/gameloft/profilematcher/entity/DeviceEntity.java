package com.gameloft.profilematcher.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="device")
public class DeviceEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String model;
    private String carrier;
    private String firmware;
}
