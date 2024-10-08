package com.gameloft.profilematcher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID playerId;
    private String credential;
    private OffsetDateTime created;
    private OffsetDateTime modified;
    private OffsetDateTime lastSession;
    private Integer totalSpent;
    private Integer totalRefund;
    private Integer totalTransactions;
    private OffsetDateTime lastPurchase;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "activeCampaigns", joinColumns = @JoinColumn(name = "profile_id"))
    private List<String> activeCampaigns;
    @OneToMany(
            targetEntity = DeviceEntity.class,
            cascade = {CascadeType.ALL, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<DeviceEntity> devices;
    private Integer level;
    private Integer xp;
    private Integer totalPlaytime;
    private String country;
    private String language;
    private OffsetDateTime birthdate;
    private String gender;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private List<ItemEntity> inventory;
    @ManyToOne
    @JoinColumn(name = "clan_id")
    private ClanEntity clan;
    private String _customField;
}
