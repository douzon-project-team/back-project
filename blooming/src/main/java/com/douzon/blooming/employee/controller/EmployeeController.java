package com.douzon.blooming.employee.controller;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 로그인
     */
    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto dto){
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
    public ResponseEntity<Void> idCheck(@RequestBody String id){
        return ResponseEntity
                .status(employeeService.idCheck(id) == null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    @GetMapping("/employee-no-check")
    public ResponseEntity<Void> idCheck(@RequestBody Long employeeNo){
        return ResponseEntity
                .status(employeeService.employeeNoCheck(employeeNo) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .build();
    }

    /**
     * 사원 등록
     */
    @PostMapping("/insert")
    public ResponseEntity<Void> insertEmployee(@RequestBody RequestEmployeeDto dto){
        // 추후 Security적용 관리자 권한 check
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

    @GetMapping("/{employeeNo}")
    public ResponseEntity<ResponseEmployeeDto> getEmployeeByNo(@PathVariable Long employeeNo){
        return
                new ResponseEntity<>(employeeService.findEmployeeByNo(employeeNo), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseListEmployeeDto> findEmployeeList(@RequestBody EmployeeSearchDto searchDto,
                                                                    @RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "8") Integer size){
        return new ResponseEntity<>(employeeService.findEmployeeListWithFilter(searchDto, page, size), HttpStatus.OK);
    }

    /**
     * 사원 삭제
     */
    @DeleteMapping("/{employeeNo}/delete-employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeNo){
        log.error(String.valueOf(employeeNo));
        // 추후 Security적용 관리자 권한 check
        employeeService.deleteEmployee(employeeNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 수정(id, password, name, img, tel, email)
     */
    @PutMapping("/{employeeNo}/update-id")
    public ResponseEntity<Void> updateId(@PathVariable Long employeeNo, String id){
        // 추후 Security적용 관리자 권한 check
        employeeService.updateId(employeeNo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeNo}/update-name")
    public ResponseEntity<Void> updateName(@PathVariable Long employeeNo, String name){
        // 추후 Security적용 관리자 권한 check
        employeeService.updateName(employeeNo, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeNo}/update-password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long employeeNo,@RequestBody String password){
        employeeService.updatePassword(employeeNo, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeNo}/update-img")
    public ResponseEntity<Void> updateImg(@PathVariable Long employeeNo, String img){
        employeeService.updateImg(employeeNo, img);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeNo}/update-tel")
    public ResponseEntity<Void> updateTel(@PathVariable Long employeeNo, String tel){
        employeeService.updateTel(employeeNo, tel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{employeeNo}/update-email")
    public ResponseEntity<Void> updateEmail(@PathVariable Long employeeNo, String email){
        employeeService.updateEmail(employeeNo, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



