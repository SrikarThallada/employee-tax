package com.imaginnovate.employeetax.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class EmployeeTaxValidator implements ConstraintValidator<EmployeeTaxValidate, EmployeeTaxDTO> {

    private static final String EMPLOYEE_ID_REGEX = "^E[0-9]{3}$"; // Example: E123
    private static final String PHONE_NUMBER_REGEX = "^[0-9]{10}$"; // Example: 1234567890

    private boolean checkField(String value, String fieldName, String message, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkDOJ(LocalDate value, String fieldName, String message, ConstraintValidatorContext context) {
        if (value == null) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkSalary(Double value, String fieldName, String message, ConstraintValidatorContext context) {
        if (value == null) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean checkSalaryValue(Double value, String fieldName, String message, ConstraintValidatorContext context) {
        if (value <= 0) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean phoneNumberPattern(List<String> phoneNumbers, String fieldName, String message, ConstraintValidatorContext context) {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }

        for (String phoneNumber : phoneNumbers) {
            if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
                context.buildConstraintViolationWithTemplate(message)
                       .addPropertyNode(fieldName)
                       .addConstraintViolation();
                return false;
            }
        }

        return true;
    }

    private boolean checkEmployeeIdPattern(String value, String fieldName, String message, ConstraintValidatorContext context) {
        if (!value.matches(EMPLOYEE_ID_REGEX)) {
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(fieldName)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(EmployeeTaxDTO dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return areUserDetailsValid(dto, context);
    }

    private boolean areUserDetailsValid(EmployeeTaxDTO employeeTaxDTO, ConstraintValidatorContext context) {
        return checkField(employeeTaxDTO.getFirstName(), "firstName", "First Name is required.", context)
        && checkField(employeeTaxDTO.getLastName(), "lastName", "Last Name is required.", context)
        && checkField(employeeTaxDTO.getEmail(), "email", "Email is required.", context)
        && checkField(employeeTaxDTO.getEmployeeId(), "employeeId", "Employee ID is required.", context)
        && checkEmployeeIdPattern(employeeTaxDTO.getEmployeeId(), "employeeId", "Invalid Employee ID format.", context)
        && checkDOJ(employeeTaxDTO.getDoj(), "doj", "Date of joining is required.", context)
        && checkSalary(employeeTaxDTO.getSalary(), "salary", "Salary is required.", context)
        && checkSalaryValue(employeeTaxDTO.getSalary(), "salary", "Salary should be a positive value.", context)
        && phoneNumberPattern(employeeTaxDTO.getPhoneNumbers(), "phoneNumbers", "Invalid phone number(s).", context);
    }
}


