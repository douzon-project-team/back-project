package com.douzon.blooming.employee.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.AllEmployeeListDto;
import com.douzon.blooming.employee.dto.response.EmployeeListDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeImageService;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.token.RefreshToken;
import com.douzon.blooming.token.service.TokenService;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto,
      HttpServletResponse resp) {
    Long employeeNo = employeeService.findEmployeeNoByDto(loginEmployeeDto);
    TokenDto token = tokenService.getToken(loginEmployeeDto.getId(),
        loginEmployeeDto.getPassword(), employeeNo);
    resp.addHeader("Set-Cookie", createTokenCookie(token.getRefreshToken()).toString());
    return ResponseEntity.ok(token);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@RequestBody RefreshToken refreshToken) {
    tokenService.removeRefreshToken(refreshToken);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/me")
  public ResponseEntity<ResponseEmployeeDto> getMe() {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return ResponseEntity.ok(employeeService.getEmployeeByNo(employeeDetails.getEmployeeNo()));
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

  @GetMapping("/list/all")
  public ResponseEntity<AllEmployeeListDto> findAllEmployeeList() {
    return ResponseEntity.ok(employeeService.getAllEmployeeList());
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

  private ResponseCookie createTokenCookie(final String value) {
    return ResponseCookie.from("refreshToken", value)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(1800)
        .sameSite(SameSite.NONE.attributeValue())
        .build();
  }
}



