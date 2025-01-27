package com.elevator.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CustomerResponse implements Serializable {
    private Long id;
    private String fullName;
    private Date dateOfBirth;
    private List<String> insuranceTypes;
}
