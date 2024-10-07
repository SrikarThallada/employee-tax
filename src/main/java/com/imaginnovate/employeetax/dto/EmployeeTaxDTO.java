package com.imaginnovate.employeetax.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EmployeeTaxValidate
public class EmployeeTaxDTO {
    private String employeeId;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private List<String> phoneNumbers;
    private LocalDate doj;
    private Double salary;
}
