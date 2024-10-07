package com.imaginnovate.employeetax.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.employeetax.dto.EmployeeTaxDTO;
import com.imaginnovate.employeetax.dto.TaxDeductionResponseDTO;
import com.imaginnovate.employeetax.service.EmployeeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/employees")
public class EmployeeTaxController {

    private EmployeeService employeeService;

    public EmployeeTaxController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody @Valid EmployeeTaxDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/tax-deductions")
    public TaxDeductionResponseDTO getTaxDeductions(
            @PathVariable String employeeId
    ) {
        return employeeService.calculateTaxDeductions(employeeId);
    }
    
    
}
