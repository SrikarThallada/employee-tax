package com.imaginnovate.employeetax.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imaginnovate.employeetax.dto.EmployeeTaxDTO;
import com.imaginnovate.employeetax.dto.TaxDeductionResponseDTO;
import com.imaginnovate.employeetax.entity.Employee;
import com.imaginnovate.employeetax.exception.EmployeeTaxException;
import com.imaginnovate.employeetax.repository.EmployeeRepo;

@Service
public class EmployeeService {
    private EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void addEmployee(EmployeeTaxDTO employeeTaxDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeTaxDTO.getEmployeeId());
        employee.setFirstName(employeeTaxDTO.getFirstName());
        employee.setLastName(employeeTaxDTO.getLastName());
        employee.setEmail(employeeTaxDTO.getEmail());
        employee.setPhoneNumbers(employeeTaxDTO.getPhoneNumbers());
        employee.setDoj(employeeTaxDTO.getDoj());
        employee.setSalary(employeeTaxDTO.getSalary());
        employeeRepo.save(employee);
    }

    public TaxDeductionResponseDTO calculateTaxDeductions(String employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);

        if (employee.isEmpty()) {
            throw new EmployeeTaxException("Employee not found with the given ID");
        }

        Employee existingEmployee = employee.get();

        double employeeSalary = calculateProratedSalary(existingEmployee);
        double tax = calculateTax(employeeSalary);
        double cess = calculateCess(employeeSalary);

        TaxDeductionResponseDTO taxDeductionResponseDTO = new TaxDeductionResponseDTO();

        taxDeductionResponseDTO.setEmployeeId(employeeId);
        taxDeductionResponseDTO.setFirstName(existingEmployee.getFirstName());
        taxDeductionResponseDTO.setLastName(existingEmployee.getLastName());
        taxDeductionResponseDTO.setYearlySalary(employeeSalary);
        taxDeductionResponseDTO.setTaxAmount(tax);
        taxDeductionResponseDTO.setCessAmount(cess);

        return taxDeductionResponseDTO;
    }


    private double calculateTax(double yearlySalary) {
        double tax = 0;
        
        if (yearlySalary > 250000 && yearlySalary <= 500000) {
            tax += (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary > 500000 && yearlySalary <= 1000000) {
            tax += 250000 * 0.05;
            tax += (yearlySalary - 500000) * 0.10;
        } else if (yearlySalary > 1000000) {
            tax += 250000 * 0.05;
            tax += 500000 * 0.10;
            tax += (yearlySalary - 1000000) * 0.20;
        }
        
        return tax;
    }

    private double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }

    private double calculateProratedSalary(Employee employee) {
    // Get current date
    LocalDate now = LocalDate.now();
    
    // Financial year start and end
    LocalDate startOfFinancialYear = LocalDate.of(now.getYear(), 4, 1);
    LocalDate endOfFinancialYear = LocalDate.of(now.getYear() + 1, 3, 31);

    // Full yearly salary
    double yearlySalary = employee.getSalary();
    double monthlySalary = yearlySalary / 12;

    // Calculate months worked
    int monthsWorked = 0;

    // If the employee joined before the financial year
    if (employee.getDoj().isBefore(startOfFinancialYear)) {
        monthsWorked = 12; // Worked the entire year
    } else {
        // Calculate months from DOJ to end of financial year
        monthsWorked = Period.between(employee.getDoj().withDayOfMonth(1), endOfFinancialYear).getMonths();
    }

    // Calculate salary for full months worked
    double totalSalary = monthlySalary * monthsWorked;

    // Handle the joining month (partial month salary)
    if (employee.getDoj().isAfter(startOfFinancialYear)) {
        int daysWorkedInMonth = 30 - employee.getDoj().getDayOfMonth() + 1; // Days worked in joining month
        double dailySalary = monthlySalary / 30; // Daily salary based on 30 days in a month
        totalSalary += dailySalary * daysWorkedInMonth; // Add prorated salary for joining month
    }

    return totalSalary;
}

}
