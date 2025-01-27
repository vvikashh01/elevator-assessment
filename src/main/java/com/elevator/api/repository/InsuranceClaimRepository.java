package com.elevator.api.repository;

import com.elevator.api.entity.InsuranceClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaimEntity, Long> {
    List<InsuranceClaimEntity> findByCustomerEntityId(Long customerId);
}