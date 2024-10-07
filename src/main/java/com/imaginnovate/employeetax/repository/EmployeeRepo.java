package com.imaginnovate.employeetax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imaginnovate.employeetax.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
    
}
