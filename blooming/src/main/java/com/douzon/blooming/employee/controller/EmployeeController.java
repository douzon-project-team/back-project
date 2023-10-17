package com.douzon.blooming.employee.controller;

import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 로그인(LoginDto<id, password>)
 * 동일하게 줘도 되는가????
 * 사원 조회 시 비밀번호 확인
 */

/**
 * 사원 등록(EmployeeDto)
 *
 * ID 중복체크(id)
 *
 * 사번 중복체크(employeeNo)
 *
 * ###사원 목록 : return List<ResponseListEmployeeDto>(id, password, img를 뺀)
 * ###(default : 사원전체 / 사원 번호검색 / 이름 검색 / 관리자 ) (페이징 8개, 최대 페이지 수)
 *
 *
 * 사원 조회(EmployeeNo) : return ResponseEmployeeDto(role을 제외)
 *
 *
 * ###사원 수정(admin권한체크 : 아이디, 이름)
 *       ### (공통 : 비밀번호, 사진, 연락처, 이메일)
 *
 * 사원 삭제(admin권한체크 : EmployeeNo)
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 로그인
     */
    @GetMapping("/login")
    public ResponseEntity<Void> login(LoginDto dto){
        return ResponseEntity
                .status(employeeService.login(dto) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    /**
     * 확인(3)
     */
    @GetMapping("/password-check")
    public ResponseEntity<Void> passwordCheck(LoginDto dto){
        return ResponseEntity
                .status(employeeService.login(dto) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    @GetMapping("/id-check")
    public ResponseEntity<Void> idCheck(String id){
        return ResponseEntity
                .status(employeeService.idCheck(id) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    @GetMapping("/employee-no-check")
    public ResponseEntity<Void> idCheck(Long employeeNo){
        return ResponseEntity
                .status(employeeService.employeeNoCheck(employeeNo) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    /**
     * 사원 등록
     */
    @PostMapping("/insert")
    public ResponseEntity<Void> insertEmployee(RequestEmployeeDto dto){
        employeeService.insertEmployee(new RequestEmployeeDto(dto.getEmployeeNo(),
                                                            dto.getId(),
                                                            dto.getPassword(),
                                                            dto.getName(),
                                                            dto.getImg(),
                                                            dto.getRole(),
                                                            dto.getTel(),
                                                            dto.getEmail()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 조회(list, list(search), id)
     */
    @GetMapping("/list")
    public ResponseEntity<List<ResponseListEmployeeDto>> getAllEmployeeList(){
        return null;
    }

    @GetMapping("/list-by-search")
    public ResponseEntity<List<ResponseListEmployeeDto>> getEmployeeListByNo(Long employeeNo){
        return null;
    }

    @GetMapping("/{employeeNo}")
    public ResponseEntity<ResponseEmployeeDto> getEmployeeByNo(@PathVariable Long employeeNo){
        return new ResponseEntity<>(employeeService.getEmployeeByNo(employeeNo), HttpStatus.OK);
    }

    /**
     * 사원 삭제
     */
    @DeleteMapping("/delete-employee")
    public ResponseEntity<Void> deleteEmployee(Long employeeNo){
        // 추후 Security와 함께 관리자 권한 check
        employeeService.deleteEmployee(employeeNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 수정(id, password, name, img, tel, email)
     */
    @PutMapping("/{employeeNo}/update-id")
    public ResponseEntity<Void> updateId(@PathVariable Long employeeNo, String id){
        // 추후 Security와 함께 관리자 권한 check
        return null;
    }

    @PutMapping("/{employeeNo}/update-name")
    public ResponseEntity<Void> updateName(@PathVariable Long employeeNo, String name){
        // 추후 Security와 함께 관리자 권한 check
        return null;
    }

    @PutMapping("/{employeeNo}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long employeeNo, String password){
        return null;
    }

    @PutMapping("/{employeeNo}/update-img")
    public ResponseEntity<Void> updateImg(@PathVariable Long employeeNo, String img){
        return null;
    }

    @PutMapping("/{employeeNo}/update-tel")
    public ResponseEntity<Void> updateTel(@PathVariable Long employeeNo, String tel){
        return null;
    }

    @PutMapping("/{employeeNo}/update-email")
    public ResponseEntity<Void> updateEmail(@PathVariable Long employeeNo, String email){
        return null;
    }
}



