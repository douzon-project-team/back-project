package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.exception.EmployeeNotFoundException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public String idCheck(String id) {
        return employeeRepository.idCheck(id);
    }

    @Override
    public boolean employeeNoCheck(Long employeeNo) {
        return employeeRepository.employeeNoCheck(employeeNo) != null;
    }

    @Override
    public ResponseEmployeeDto findEmployeeByNo(Long employeeNo) {
        return employeeRepository.findEmployeeByNo(employeeNo)
                .orElseThrow(EmployeeNotFoundException :: new);
    }

    @Override
    public ResponseListEmployeeDto findEmployeeListWithFilter(EmployeeSearchDto dto, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        List<ListEmployeeDto> employeeList = employeeRepository.findEmployeeListWithFilter(dto, start, pageSize);
        int searchEmployeeCount = employeeRepository.getCountEmployees(dto);

        boolean hasNextPage = pageSize < searchEmployeeCount;
        boolean hasPreviousPage = start > 0;

        return new ResponseListEmployeeDto(employeeList, page, hasNextPage, hasPreviousPage);
    }


    @Override
    public void updateId(Long employeeNo, String id) {
        employeeRepository.updateId(employeeNo, id);
    }

    @Override
    public void updateName(Long employeeNo, String name) {
        employeeRepository.updateName(employeeNo, name);
    }

    @Override
    public void updatePassword(Long employeeNo, String password) {
        employeeRepository.updatePassword(employeeNo, password);
    }

    @Override
    public void updateImg(Long employeeNo, String img) {
        employeeRepository.updateImg(employeeNo, img);
    }

    @Override
    public void updateTel(Long employeeNo, String tel) {
        employeeRepository.updateTel(employeeNo, tel);
    }

    @Override
    public void updateEmail(Long employeeNo, String email) {
        employeeRepository.updateEmail(employeeNo, email);
    }

    @Override
    public void deleteEmployee(Long employeeNo) {
        employeeRepository.deleteEmployee(employeeNo);
    }
}
