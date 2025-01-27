package com.elevator.api.service;

import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.mapper.BeanMapper;
import com.elevator.api.model.request.CreateCustomerRequest;
import com.elevator.api.model.response.CustomerResponse;
import com.elevator.api.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BeanMapper mapper;

    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        log.info("Creating customer details for user name = " + customerRequest.getFullName());
        CustomerEntity customerEntity = mapper.mapCustomerRequestToEntity(customerRequest);
        customerRepository.save(customerEntity);
        return mapper.mapCustomerEntityToResponse(customerEntity);
    }

    public Optional<CustomerResponse> getCustomer(Long id) {
        log.info("Fetching customer details for id = " + id);
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        return customerEntity.map(entity -> mapper.mapCustomerEntityToResponse(entity));
    }

/*    public List<CustomerResponse> getAllCustomers() {
        log.info("Fetching all customers");
        List<CustomerEntity> customerEntityList = customerRepository.findAll();
         if(customerEntityList.isEmpty()) {
             return Collections.EMPTY_LIST;
         }
         return mapper.mapCustomerEntityListToResponseList(customerEntityList);
    }*/
}
