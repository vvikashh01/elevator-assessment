package com.elevator.api.controller;

import com.elevator.api.model.request.CreateCustomerRequest;
import com.elevator.api.model.response.CustomerResponse;
import com.elevator.api.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testCreateCustomer() throws Exception {
        Mockito.when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(getCustomerResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/elevator/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\": \"Vivek Kumar\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Vivek Kumar"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.insuranceTypes").isArray());

    }

    @Test
    void testGetCustomer() throws Exception {
        Mockito.when(customerService.getCustomer(anyLong())).thenReturn(Optional.of(getCustomerResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/elevator/api/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Vivek Kumar"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.insuranceTypes").isArray());
    }

    @Test
    void testGetCustomerNotFound() throws Exception {
        Mockito.when(customerService.getCustomer(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/elevator/api/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static CustomerResponse getCustomerResponse() {
        CustomerResponse response = new CustomerResponse();
        response.setId(1L);
        response.setFullName("Vivek Kumar");
        response.setDateOfBirth(new Date(631152000000L));
        response.setInsuranceTypes(List.of("Health", "Life"));
        return response;
    }
}
