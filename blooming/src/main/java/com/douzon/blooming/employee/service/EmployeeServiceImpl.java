package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.EmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public void login(LoginDto dto) {
        employeeRepository.login(dto);
    }

    @Override
    public void insertEmployee(EmployeeDto dto) {
        employeeRepository.insertEmployee(dto);
    }

    @Override
    public void updateEmployee(EmployeeDto dto) {
        employeeRepository.updateEmployee(dto);
    }
}
