package com.elevator.api.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateCustomerRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullName;
    private Date dateOfBirth;
    private List<String> insuranceTypes;
}
