package com.gameloft.profilematcher.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameloft.profilematcher.model.campaign.Campaign;
import com.gameloft.profilematcher.service.CampaignService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Campaign getCampaign() {
        try {
            File file = Paths.get("src/main/resources/static/campaign.json").toFile();

            return objectMapper.readValue(file, Campaign.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading campaign data from file.", e);
        }
    }
}
