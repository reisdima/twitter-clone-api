package vincenzo.caio.twittercloneapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vincenzo.caio.twittercloneapi.dto.CampaignDto;
import vincenzo.caio.twittercloneapi.model.Campaign;
import vincenzo.caio.twittercloneapi.service.CampaignService;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createNewCampaign(@RequestBody CampaignDto campaignDto) {
        Campaign newCampaign = service.createCampaign(campaignDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("The campaign was successfully created. " + newCampaign);
    }

    @GetMapping
    public ResponseEntity<?> getAllCampaign() {
        List<Campaign> campaigns = service.getAllCampaigns();
        return ResponseEntity.status(HttpStatus.OK).body(campaigns);
    }

    @GetMapping("/points")
    public ResponseEntity<?> getPointsByUser(@RequestParam String email) {
        int points = service.calculatePointsForUser(email);
        return ResponseEntity.status(HttpStatus.OK).body("User " + email + " has " + points + " points");
    }

    @PutMapping
    public ResponseEntity<?> updateCampaignPhrase(@RequestBody CampaignDto campaign) {
        Campaign updatedCampaign = service.updateCampaignPhrase(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCampaign);
    }
}
