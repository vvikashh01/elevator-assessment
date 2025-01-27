package com.elevator.api.mapper;

import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.entity.InsuranceClaimEntity;
import com.elevator.api.model.request.CreateCustomerRequest;
import com.elevator.api.model.request.CreateInsuranceClaimRequest;
import com.elevator.api.model.response.CustomerResponse;
import com.elevator.api.model.response.InsuranceClaimResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeanMapper {


    CustomerEntity mapCustomerRequestToEntity(CreateCustomerRequest customerRequest);
    

    List<CustomerResponse> mapCustomerEntityListToResponseList(List<CustomerEntity> customerEntityList);

    InsuranceClaimResponse mapInsuranceClaimEntityToResponse(InsuranceClaimEntity save);

    List<InsuranceClaimResponse> mapInsuranceClaimEntitiesToResponse(List<InsuranceClaimEntity> byCustomerId);

    InsuranceClaimEntity mapInsuranceClaimRequestToEntity(CreateInsuranceClaimRequest claimRequest);

    CustomerResponse mapCustomerEntityToResponse(CustomerEntity customerEntity);
}
