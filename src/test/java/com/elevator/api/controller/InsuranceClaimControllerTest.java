package com.elevator.api.controller;

import com.elevator.api.model.request.CreateInsuranceClaimRequest;
import com.elevator.api.model.response.InsuranceClaimResponse;
import com.elevator.api.service.InsuranceClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InsuranceClaimController.class)
class InsuranceClaimControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceClaimService insuranceClaimService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateInsuranceClaim() throws Exception {
        Mockito.when(insuranceClaimService.createInsuranceClaim(anyLong(), any(CreateInsuranceClaimRequest.class))).thenReturn(getClaimResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/elevator/api/claims/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(getClaimRequestJson())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.claimType").value("Health"))
                .andExpect(jsonPath("$.cost").value(1000.0))
                .andExpect(jsonPath("$.status").value("open"));

    }

    @Test
    void testUpdateInsuranceClaim() throws Exception {
        Mockito.when(insuranceClaimService.updateInsuranceClaim(anyLong(), any(CreateInsuranceClaimRequest.class))).thenReturn(getClaimResponse());

        mockMvc.perform(MockMvcRequestBuilders.put("/elevator/api/claims/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(getClaimRequestJson())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.claimType").value("Health"))
                .andExpect(jsonPath("$.cost").value(1000.0))
                .andExpect(jsonPath("$.status").value("open"));
    }

    @Test
    void testDeleteInsuranceClaim() throws Exception {
        Mockito.doNothing().when(insuranceClaimService).deleteInsuranceClaim(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/elevator/api/claims/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetClaimsByCustomerId() throws Exception {
        Mockito.when(insuranceClaimService.getClaimsByCustomerId(anyLong())).thenReturn(List.of(getClaimResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/elevator/api/claims/customer/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L));
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

    private static String getClaimRequestJson() {
        try {
            return objectMapper.writeValueAsString(getClaimRequest());
        } catch (Exception e) {
            return null;
        }
    }
}
