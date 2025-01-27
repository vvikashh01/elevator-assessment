package com.elevator.api.controller;

import com.elevator.api.constant.ElevatorApplicationConstant;
import com.elevator.api.entity.CustomerEntity;
import com.elevator.api.model.request.CreateCustomerRequest;
import com.elevator.api.model.response.CustomerResponse;
import com.elevator.api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(ElevatorApplicationConstant.REQUEST_MAPPING_BASE_PATH + "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest customerRequest) {
        log.info("create customer request received for user name : {}", customerRequest.getFullName());
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        log.info("get customer request received for user id : {}", id);
        Optional<CustomerResponse> customer = customerService.getCustomer(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

/*    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("get all customers request received");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }*/
}
