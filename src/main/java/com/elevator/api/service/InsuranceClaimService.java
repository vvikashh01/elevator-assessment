package com.elevator.api.service;

import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.entity.InsuranceClaimEntity;
import com.elevator.api.exception.ServiceException;
import com.elevator.api.mapper.BeanMapper;
import com.elevator.api.model.request.CreateInsuranceClaimRequest;
import com.elevator.api.model.response.InsuranceClaimResponse;
import com.elevator.api.repository.CustomerRepository;
import com.elevator.api.repository.InsuranceClaimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InsuranceClaimService {
    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BeanMapper mapper;

    public InsuranceClaimResponse createInsuranceClaim(Long customerId, CreateInsuranceClaimRequest claimRequest) throws ServiceException {
        log.info("Creating insurance claim for customer id = " + customerId);
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if (customer.isPresent() && customer.get().getInsuranceTypes().contains(claimRequest.getClaimType())) {
            InsuranceClaimEntity claimEntity = mapper.mapInsuranceClaimRequestToEntity(claimRequest);
            claimEntity.setCustomerEntity(customer.get());
            return mapper.mapInsuranceClaimEntityToResponse(insuranceClaimRepository.save(claimEntity));
        } else {
            throw new ServiceException("Customer does not have the correct type of insurance.");
        }
    }

    public InsuranceClaimResponse updateInsuranceClaim(Long id, CreateInsuranceClaimRequest claimRequest) {
        log.info("Updating insurance claim for id = " + id);
        Optional<InsuranceClaimEntity> existingClaim = insuranceClaimRepository.findById(id);
        if (existingClaim.isPresent()) {
            InsuranceClaimEntity updatedClaim = existingClaim.get();
            updatedClaim.setStatus(claimRequest.getStatus());
            return mapper.mapInsuranceClaimEntityToResponse(insuranceClaimRepository.save(updatedClaim));
        }
        return null;
    }

    public void deleteInsuranceClaim(Long id) {
        log.info("Deleting insurance claim for id = " + id);
        insuranceClaimRepository.deleteById(id);
    }

    public List<InsuranceClaimResponse> getClaimsByCustomerId(Long customerId) {
        log.info("Fetching insurance claims for customer id = " + customerId);
        return mapper.mapInsuranceClaimEntitiesToResponse(insuranceClaimRepository.findByCustomerEntityId(customerId));
    }
}
