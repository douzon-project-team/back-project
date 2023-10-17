package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean login(LoginDto dto) {
        return Objects.equals(employeeRepository.login(dto), dto.getPassword());
    }

    @Override
    public void insertEmployee(RequestEmployeeDto dto) {
        employeeRepository.insertEmployee(dto);
    }

    @Override
    public boolean idCheck(String id) {
        return employeeRepository.idCheck(id) != null;
    }

    @Override
    public boolean employeeNoCheck(Long employeeNo) {
        return employeeRepository.employeeNoCheck(employeeNo) != null;
    }

    @Override
    public ResponseEmployeeDto getEmployeeByNo(Long employeeNo) {
        return employeeRepository.getEmployeeByNo(employeeNo);
    }

    @Override
    public void updateEmployee(RequestEmployeeDto dto) {
        employeeRepository.updateEmployee(dto);
    }

    @Override
    public void deleteEmployee(Long employeeNo) {
        employeeRepository.deleteEmployee(employeeNo);
    }
}
