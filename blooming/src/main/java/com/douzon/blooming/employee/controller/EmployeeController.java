package com.douzon.blooming.employee.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.EmployeeListDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeImageService;
import com.douzon.blooming.employee.service.EmployeeService;
import java.net.MalformedURLException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final EmployeeImageService employeeImageService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto) {
    return ResponseEntity.ok(employeeService.login(loginEmployeeDto));
  }

  @GetMapping("/{employeeNo}")
  public ResponseEntity<ResponseEmployeeDto> getEmployeeByNo(@PathVariable Long employeeNo) {
    return ResponseEntity.ok(employeeService.getEmployeeByNo(employeeNo));
  }

  @GetMapping("/list")
  public ResponseEntity<PageDto<EmployeeListDto>> findEmployeeList(
      @ModelAttribute EmployeeSearchDto searchDto) {
    return ResponseEntity.ok(employeeService.getEmployeeList(searchDto));
  }

  @PutMapping("/{employeeNo}")
  public ResponseEntity<Void> updateEmployee(@PathVariable Long employeeNo,
      @RequestBody @Valid UpdateEmployeeDto updateEmployeeDto) {
    employeeService.updateEmployee(updateEmployeeDto, employeeNo);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{employeeNo}/image")
  public UrlResource getImage(@PathVariable Long employeeNo) throws MalformedURLException {
    return employeeImageService.getEmployeeImage(employeeNo);
  }

  @PostMapping(value = "/{employeeNo}/image", consumes = "multipart/form-data")
  public ResponseEntity<Void> addImage(@PathVariable Long employeeNo,
      @RequestParam MultipartFile file) {
    employeeImageService.addEmployeeImage(file, employeeNo);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{employeeNo}/image", consumes = "multipart/form-data")
  public ResponseEntity<Void> modifyImage(@PathVariable Long employeeNo,
      @RequestParam MultipartFile file) {
    employeeImageService.updateEmployeeImage(file, employeeNo);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{employeeNo}/image")
  public ResponseEntity<Void> deleteImage(@PathVariable Long employeeNo) {
    employeeImageService.deleteEmployeeImage(employeeNo);
    return ResponseEntity.noContent().build();
  }
}



