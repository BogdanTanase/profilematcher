package com.gameloft.profilematcher.service;

import com.gameloft.profilematcher.exception.ElementAlreadyExistsException;
import com.gameloft.profilematcher.model.profile.Clan;

import java.util.NoSuchElementException;

public interface ClanService {
    Clan getClan(String clanId) throws IllegalArgumentException, NoSuchElementException;

    Clan createClan(Clan clan) throws IllegalArgumentException, ElementAlreadyExistsException;

    void deleteClan(String clanId)  throws IllegalArgumentException;;
}
