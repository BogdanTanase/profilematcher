package com.gameloft.profilematcher.mapper;

import com.gameloft.profilematcher.entity.ClanEntity;
import com.gameloft.profilematcher.entity.DeviceEntity;
import com.gameloft.profilematcher.entity.ItemEntity;
import com.gameloft.profilematcher.entity.ProfileEntity;
import com.gameloft.profilematcher.model.profile.Clan;
import com.gameloft.profilematcher.model.profile.Device;
import com.gameloft.profilematcher.model.profile.Profile;
import com.gameloft.profilematcher.model.profile.ProfileRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileModelEntityMapper {

    public static ProfileEntity toEntity(Profile model) {
        if (model == null) {
            return null;
        }

        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        for (Device device : model.devices()) {
            deviceEntityList.add(DeviceModelEntityMapper.toEntity(device));
        }

        List<ItemEntity> inventoryEntityList = new ArrayList<>();
        for (Map.Entry<String, Integer> item : model.inventory().entrySet()) {
            inventoryEntityList.add(ItemEntity.builder()
                    .itemName(item.getKey())
                    .quantity(item.getValue())
                    .build());
        }
        ClanEntity clanEntity = ClanModelEntityMapper.toEntity(model.clan());

        return ProfileEntity.builder()
                .playerId(model.playerId())
                .credential(model.credential())
                .created(model.created())
                .modified(model.modified())
                .lastSession(model.lastSession())
                .totalSpent(model.totalSpent())
                .totalRefund(model.totalRefund())
                .totalTransactions(model.totalTransactions())
                .lastPurchase(model.lastPurchase())
                .activeCampaigns(model.activeCampaigns())
                .devices(deviceEntityList)
                .level(model.level())
                .xp(model.xp())
                .totalPlaytime(model.totalPlaytime())
                .country(model.country())
                .language(model.language())
                .birthdate(model.birthdate())
                .gender(model.gender())
                .inventory(inventoryEntityList)
                .clan(clanEntity)
                ._customField(model._customField())
                .build();
    }

    public static Profile toModel(ProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        List<Device> deviceList = new ArrayList<>();
        for (DeviceEntity deviceEntity : entity.getDevices()) {
            deviceList.add(Device.builder()
                    .id(deviceEntity.getId())
                    .model(deviceEntity.getModel())
                    .carrier(deviceEntity.getCarrier())
                    .firmware(deviceEntity.getFirmware())
                    .build());
        }

        Map<String, Integer> inventoryMap = new HashMap<>();
        for (ItemEntity itemEntity : entity.getInventory()) {
            inventoryMap.put(itemEntity.getItemName(), itemEntity.getQuantity());
        }
        Clan clan = Clan.builder()
                .id(String.valueOf(entity.getClan().getId()))
                .name(entity.getClan().getName())
                .build();

        return Profile.builder()
                .playerId(entity.getPlayerId())
                .credential(entity.getCredential())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .lastSession(entity.getLastSession())
                .totalSpent(entity.getTotalSpent())
                .totalRefund(entity.getTotalRefund())
                .totalTransactions(entity.getTotalTransactions())
                .lastPurchase(entity.getLastPurchase())
                .activeCampaigns(entity.getActiveCampaigns())
                .devices(deviceList)
                .level(entity.getLevel())
                .xp(entity.getXp())
                .totalPlaytime(entity.getTotalPlaytime())
                .country(entity.getCountry())
                .language(entity.getLanguage())
                .birthdate(entity.getBirthdate())
                .gender(entity.getGender())
                .inventory(inventoryMap)
                .clan(clan)
                ._customField(entity.get_customField())
                .build();
    }

    public static Profile toModel(ProfileRequest profileRequest) {
        if (profileRequest == null) {
            return null;
        }

        return Profile.builder()
                .playerId(profileRequest.player_id())
                .credential(profileRequest.credential())
                .created(profileRequest.created())
                .modified(profileRequest.modified())
                .lastSession(profileRequest.last_session())
                .totalSpent(profileRequest.total_spent())
                .totalRefund(profileRequest.total_refund())
                .totalTransactions(profileRequest.total_transactions())
                .lastPurchase(profileRequest.last_purchase())
                .activeCampaigns(profileRequest.active_campaigns())
                .devices(profileRequest.devices())
                .level(profileRequest.level())
                .xp(profileRequest.xp())
                .totalPlaytime(profileRequest.total_playtime())
                .country(profileRequest.country())
                .language(profileRequest.language())
                .birthdate(profileRequest.birthdate())
                .gender(profileRequest.gender())
                .inventory(profileRequest.inventory())
                .clan(profileRequest.clan())
                ._customField(profileRequest._customField())
                .build();
    }
}
