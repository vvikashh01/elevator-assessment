package com.elevator.api.service;

import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.mapper.BeanMapper;
import com.elevator.api.model.request.CreateCustomerRequest;
import com.elevator.api.model.response.CustomerResponse;
import com.elevator.api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BeanMapper mapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomer() {
        CreateCustomerRequest request = getCreateCustomerRequest();

        Mockito.when(mapper.mapCustomerRequestToEntity(any(CreateCustomerRequest.class))).thenReturn(getCustomerEntity());
        Mockito.when(customerRepository.save(any(CustomerEntity.class))).thenReturn(getCustomerEntity());
        Mockito.when(mapper.mapCustomerEntityToResponse(any(CustomerEntity.class))).thenReturn(getCustomerResponse());

        CustomerResponse result = customerService.createCustomer(request);
        assertEquals(1L, result.getId());
        assertEquals("Vivek Kumar", result.getFullName());
    }

    @Test
    void testGetCustomer() {
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(getCustomerEntity()));
        Mockito.when(mapper.mapCustomerEntityToResponse(any(CustomerEntity.class))).thenReturn(getCustomerResponse());

        Optional<CustomerResponse> result = customerService.getCustomer(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Vivek Kumar", result.get().getFullName());
    }

    private static CustomerEntity getCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFullName("Vivek Kumar");
        return customerEntity;
    }

    private static CustomerResponse getCustomerResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setId(1L);
        response.setFullName("Vivek Kumar");
        response.setDateOfBirth(new Date(631152000000L));
        response.setInsuranceTypes(List.of("Health", "Life"));
        return response;
    }

    private static CreateCustomerRequest getCreateCustomerRequest() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName("Vivek Kumar");
        return request;
    }

}
