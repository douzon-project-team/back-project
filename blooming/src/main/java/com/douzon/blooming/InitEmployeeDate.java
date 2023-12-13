package com.douzon.blooming;

import com.douzon.blooming.auth.EmployeeRole;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeImageService;
import com.douzon.blooming.employee.service.EmployeeService;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitEmployeeDate {

  private final EmployeeService employeeService;
  private final EmployeeImageService employeeImageService;

  @PostConstruct
  public void init() {
    List<InsertEmployeeDto> employeeList = List.of(
        new InsertEmployeeDto(200001L, "admin", "1234", "관리자", EmployeeRole.ROLE_ADMIN,
            "01045965429", "admin@admin.com"),
        new InsertEmployeeDto(200002L, "m1", "1234", "박상웅", EmployeeRole.ROLE_MEMBER,
            "01012345678", "dawndaw@google.com"),
        new InsertEmployeeDto(200003L, "m2", "1234", "송재근", EmployeeRole.ROLE_MEMBER,
            "01053132131", "faw312gaw@google.com"),
        new InsertEmployeeDto(200004L, "m3", "1234", "윤찬웅", EmployeeRole.ROLE_MEMBER,
            "01012416432", "aweaw321g@google.com"),
        new InsertEmployeeDto(200005L, "m4", "1234", "오수민", EmployeeRole.ROLE_MEMBER,
            "01052325678", "fa14@google.com"),
        new InsertEmployeeDto(200006L, "m5", "1234", "김철수", EmployeeRole.ROLE_MEMBER,
            "01053115135", "awgjy124@google.com"),
        new InsertEmployeeDto(200007L, "m6", "1234", "박한나", EmployeeRole.ROLE_MEMBER,
            "01019349543", "jtjyt856@google.com"),
        new InsertEmployeeDto(200008L, "m7", "1234", "김지철", EmployeeRole.ROLE_MEMBER,
            "01094375112", "awfvvbf654@google.com"),
        new InsertEmployeeDto(200009L, "m8", "1234", "한지민", EmployeeRole.ROLE_MEMBER,
            "01063216912", "awdawgjki44@google.com"),
        new InsertEmployeeDto(2000010L, "m9", "1234", "이지훈", EmployeeRole.ROLE_MEMBER,
            "01042118912", "esfse1111@google.com"));
    employeeList.forEach(employeeService::signup);
  }
}