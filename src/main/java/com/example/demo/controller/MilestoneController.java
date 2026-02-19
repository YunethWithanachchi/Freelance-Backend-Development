package com.example.demo.controller;
import com.example.demo.dto.MilestoneRequest;
import com.example.demo.dto.MilestoneResponse;
import com.example.demo.service.MilestoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @PostMapping
    public ResponseEntity<MilestoneResponse> createMilestone(@Valid @RequestBody MilestoneRequest request) {
        return ResponseEntity.ok(milestoneService.createMilestone(request));
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<MilestoneResponse>> getMilestonesByContract(@PathVariable String contractId) {
        return ResponseEntity.ok(milestoneService.getMilestonesByContract(contractId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MilestoneResponse> updateMilestoneStatus(@PathVariable String id,
                                                                   @RequestParam String status) {
        return ResponseEntity.ok(milestoneService.updateMilestoneStatus(id, status));
    }
}
