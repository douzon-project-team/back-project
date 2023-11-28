package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.exception.ImageUploadException;
import com.douzon.blooming.employee.exception.NotFoundImageException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemoryEmployeeImageService implements
    EmployeeImageService {

  private static final String ABSOLUTE_PATH = new File("").getAbsolutePath();
  private static final String PATH = "blooming/image";
  private static final String SEPARATOR = "/";

  private final EmployeeRepository employeeRepository;

  @Override
  public void addEmployeeImage(MultipartFile multipartFile, Long employeeNo) {
    String imageUrl = uploadImageInMemory(multipartFile);
    employeeRepository.updateEmployeeImage(imageUrl, employeeNo);
  }

  @Override
  public void updateEmployeeImage(MultipartFile multipartFile, Long employeeNo) {
    addEmployeeImage(multipartFile, employeeNo);
  }

  @Override
  public UrlResource getEmployeeImage(Long employeeNo) throws MalformedURLException {
    String imageUrl = employeeRepository.findEmployeeImageByEmployeeNo(employeeNo);
    if (imageUrl == null) {
      throw new NotFoundImageException();
    }

    return new UrlResource(
        "file:" + ABSOLUTE_PATH + File.separator + PATH + File.separator + imageUrl);
  }

  @Override
  public void deleteEmployeeImage(Long employeeNo) {
    employeeRepository.updateEmployeeImage(null, employeeNo);
  }

  private String uploadImageInMemory(MultipartFile multipartFile) {
    if (multipartFile == null || multipartFile.isEmpty()
        || multipartFile.getContentType() == null) {
      throw new ImageUploadException("Invalid image file");
    }

    String contentType = multipartFile.getContentType();
    String extension;

    if ("image/jpeg".equals(contentType)) {
      extension = ".jpg";
    } else if ("image/png".equals(contentType)) {
      extension = ".png";
    } else {
      throw new ImageUploadException("Unsupported image format");
    }

    UUID uuid = UUID.randomUUID();
    String fileName = uuid + extension;
    try {
      File file = new File(ABSOLUTE_PATH + SEPARATOR + PATH, fileName);
      multipartFile.transferTo(file);
      return fileName;
    } catch (IOException e) {
      e.printStackTrace();
      throw new ImageUploadException("Failed to upload image");
    }
  }
}
