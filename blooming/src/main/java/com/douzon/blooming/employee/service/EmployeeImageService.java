package com.douzon.blooming.employee.service;

import java.net.MalformedURLException;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeImageService {

  void addEmployeeImage(MultipartFile file, Long employeeNo);

  void updateEmployeeImage(MultipartFile file, Long employeeNo);

  UrlResource getEmployeeImage(Long employeeNo) throws MalformedURLException;

  void deleteEmployeeImage(Long employeeNo);
}
