package com.gameloft.profilematcher.controller;

import com.gameloft.profilematcher.model.profile.Clan;
import com.gameloft.profilematcher.service.ClanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clan/")
public class ClanController {
    private final ClanService clanService;

    /**
     * Retrieves a clan based on the clan ID.
     *
     * @param clanId the clan's unique identifier
     * @return Clan object
     */
    @GetMapping("/{clanId}")
    public Clan getClan(@PathVariable String clanId) {
        return clanService.getClan(clanId);
    }

    /**
     * Creates a new clan based on the provided clan data.
     *
     * @param clan the clan data
     * @return created Clan object
     */
    @PostMapping("/")
    public Clan createClan(@RequestBody Clan clan) {
        return clanService.createClan(clan);
    }

    /**
     * Updates an existing clan based on the provided clan ID.
     *
     * @param clanId the clan's unique identifier
     * @return updated Clan object
     */
    @DeleteMapping("/{clanId}")
    public void deleteClan(@PathVariable String clanId) {
        clanService.deleteClan(clanId);
    }
}
