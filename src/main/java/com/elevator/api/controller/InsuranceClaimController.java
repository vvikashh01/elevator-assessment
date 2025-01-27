package com.elevator.api.controller;

import com.elevator.api.constant.ElevatorApplicationConstant;
import com.elevator.api.entity.InsuranceClaimEntity;
import com.elevator.api.model.request.CreateInsuranceClaimRequest;
import com.elevator.api.model.response.InsuranceClaimResponse;
import com.elevator.api.service.InsuranceClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(ElevatorApplicationConstant.REQUEST_MAPPING_BASE_PATH + "/claims")
public class InsuranceClaimController {

    @Autowired
    private InsuranceClaimService insuranceClaimService;

    @PostMapping("/{customerId}")
    public ResponseEntity<InsuranceClaimResponse> createInsuranceClaim(@PathVariable Long customerId, @RequestBody CreateInsuranceClaimRequest claimRequest) {
        log.info("create insurance claim request received for customer id : {}", customerId);
        return ResponseEntity.ok(insuranceClaimService.createInsuranceClaim(customerId, claimRequest));

    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceClaimResponse> updateInsuranceClaim(@PathVariable Long id, @RequestBody CreateInsuranceClaimRequest claimRequest) {
        log.info("update insurance claim request received for claim id : {}", id);
        return ResponseEntity.ok(insuranceClaimService.updateInsuranceClaim(id, claimRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsuranceClaim(@PathVariable Long id) {
        log.info("delete insurance claim request received for claim id : {}", id);
        insuranceClaimService.deleteInsuranceClaim(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<InsuranceClaimResponse>> getClaimsByCustomerId(@PathVariable Long customerId) {
        log.info("get claims request received for customer id : {}", customerId);
        return ResponseEntity.ok(insuranceClaimService.getClaimsByCustomerId(customerId));
    }
}
