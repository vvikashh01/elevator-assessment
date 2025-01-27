package com.elevator.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class InsuranceClaimResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String claimType;
    private Date date;
    private String status;
    private Double cost;
}
