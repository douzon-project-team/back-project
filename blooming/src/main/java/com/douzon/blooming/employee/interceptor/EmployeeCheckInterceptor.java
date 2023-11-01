package com.douzon.blooming.employee.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class EmployeeCheckInterceptor implements HandlerInterceptor {

  private static final String EMPLOYEE_PREFIX = "employees/";

  @Override
  public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler) {

    if (!request.getMethod().equalsIgnoreCase("GET")) {
      Pattern pattern = Pattern.compile("employees/(\\d+)");
      Matcher matcher = pattern.matcher(EMPLOYEE_PREFIX);

      if (matcher.find()) {
        String numberPart = matcher.group(1);
        Long employeeNo = Long.parseLong(numberPart);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetails employee = (EmployeeDetails) authentication.getPrincipal();
        return employee.getEmployeeNo().equals(employeeNo);
      }
    }
    return true;
  }
}