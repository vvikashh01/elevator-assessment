package com.elevator.api.service;

import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.entity.InsuranceClaimEntity;
import com.elevator.api.exception.ServiceException;
import com.elevator.api.mapper.BeanMapper;
import com.elevator.api.model.request.CreateInsuranceClaimRequest;
import com.elevator.api.model.response.InsuranceClaimResponse;
import com.elevator.api.repository.CustomerRepository;
import com.elevator.api.repository.InsuranceClaimRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class InsuranceClaimServiceTest {

    @Mock
    private InsuranceClaimRepository insuranceClaimRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BeanMapper mapper;

    @InjectMocks
    private InsuranceClaimService insuranceClaimService;

    @Test
    void testCreateInsuranceClaim() throws ServiceException {
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(getCustomerEntity()));
        Mockito.when(mapper.mapInsuranceClaimRequestToEntity(any(CreateInsuranceClaimRequest.class))).thenReturn(getInsuranceClaimEntity());
        Mockito.when(insuranceClaimRepository.save(any(InsuranceClaimEntity.class))).thenReturn(getInsuranceClaimEntity());
        Mockito.when(mapper.mapInsuranceClaimEntityToResponse(any(InsuranceClaimEntity.class))).thenReturn(getClaimResponse());

        InsuranceClaimResponse result = insuranceClaimService.createInsuranceClaim(1L, getClaimRequest());

        assertEquals(1L, result.getId());
        assertEquals("Health", result.getClaimType());
    }

    @Test
    void testCreateInsuranceClaimThrowsException() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setInsuranceTypes(List.of("Life"));

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            insuranceClaimService.createInsuranceClaim(1L, getClaimRequest());
        });

        assertEquals("Customer does not have the correct type of insurance.", exception.getMessage());
    }

    @Test
    void testUpdateInsuranceClaim() {
        CreateInsuranceClaimRequest request = new CreateInsuranceClaimRequest();
        request.setStatus("Approved");

        InsuranceClaimEntity existingClaim = new InsuranceClaimEntity();
        existingClaim.setId(1L);
        existingClaim.setStatus("Pending");

        InsuranceClaimResponse response = new InsuranceClaimResponse();
        response.setId(1L);
        response.setStatus("Approved");

        Mockito.when(insuranceClaimRepository.findById(anyLong())).thenReturn(Optional.of(existingClaim));
        Mockito.when(insuranceClaimRepository.save(any(InsuranceClaimEntity.class))).thenReturn(existingClaim);
        Mockito.when(mapper.mapInsuranceClaimEntityToResponse(any(InsuranceClaimEntity.class))).thenReturn(response);

        InsuranceClaimResponse result = insuranceClaimService.updateInsuranceClaim(1L, request);

        assertEquals(1L, result.getId());
        assertEquals("Approved", result.getStatus());
    }

    @Test
    void testDeleteInsuranceClaim() {
        Mockito.doNothing().when(insuranceClaimRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> insuranceClaimService.deleteInsuranceClaim(1L));
    }

    @Test
    void testGetClaimsByCustomerId() {
        Mockito.when(insuranceClaimRepository.findByCustomerEntityId(anyLong())).thenReturn(List.of(getInsuranceClaimEntity()));
        Mockito.when(mapper.mapInsuranceClaimEntitiesToResponse(any(List.class))).thenReturn(List.of(getClaimResponse()));

        List<InsuranceClaimResponse> result = insuranceClaimService.getClaimsByCustomerId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Health", result.get(0).getClaimType());
    }

    private static InsuranceClaimResponse getClaimResponse() {
        InsuranceClaimResponse response = new InsuranceClaimResponse();
        response.setId(1L);
        response.setClaimType("Health");
        response.setCost(1000.0);
        response.setStatus("open");
        response.setDate(new Date());
        return response;
    }

    private static CreateInsuranceClaimRequest getClaimRequest() {
        CreateInsuranceClaimRequest request = new CreateInsuranceClaimRequest();
        request.setClaimType("Health");
        request.setCost(1000.0);
        request.setStatus("open");
        request.setDate(new Date());
        return request;
    }

    private static CustomerEntity getCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setInsuranceTypes(List.of("Health"));
        return customerEntity;
    }

    private static InsuranceClaimEntity getInsuranceClaimEntity() {
        InsuranceClaimEntity claimEntity = new InsuranceClaimEntity();
        claimEntity.setId(1L);
        claimEntity.setClaimType("Health");
        return claimEntity;
    }
}
