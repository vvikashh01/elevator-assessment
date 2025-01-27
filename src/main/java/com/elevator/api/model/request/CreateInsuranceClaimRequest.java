package com.elevator.api.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class CreateInsuranceClaimRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String claimType;
    private Date date;
    private String status;
    private Double cost;
}
