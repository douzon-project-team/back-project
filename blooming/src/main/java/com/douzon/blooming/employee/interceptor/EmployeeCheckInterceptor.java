package com.douzon.blooming.employee.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import java.util.Objects;
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

  private static final String EMPLOYEE_PREFIX = "/employees/";

  @Override
  public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    String path = request.getServletPath();
    if (!request.getMethod().equalsIgnoreCase("GET") && path
        .startsWith(EMPLOYEE_PREFIX)) {
      Pattern pattern = Pattern.compile(EMPLOYEE_PREFIX + "(\\d+)");
      Matcher matcher = pattern.matcher(path);

      if (matcher.find()) {
        String numberPart = matcher.group(1);
        Long employeeNo = Long.parseLong(numberPart);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> Objects.equals(
            grantedAuthority.getAuthority(), "ADMIN"))) {
          return true;
        }
        EmployeeDetails employee = (EmployeeDetails) authentication.getPrincipal();
        return employee.getEmployeeNo().equals(employeeNo);
      }
    }
    return true;
  }
}