package com.douzon.blooming.token.controller;

import com.douzon.blooming.token.dto.request.RequestReissueTokenDto;
import com.douzon.blooming.token.dto.response.ReissueTokenDto;
import com.douzon.blooming.token.exception.NotFoundRefreshTokenException;
import com.douzon.blooming.token.service.TokenService;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

  private final TokenService tokenService;

  @PostMapping("/reissue")
  public ResponseEntity<ReissueTokenDto> reissueToken(@RequestBody RequestReissueTokenDto dto,
      HttpServletRequest req) {
    String refreshToken = "";
    if (req.getCookies() != null) {
      refreshToken = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("refreshToken"))
          .findAny()
          .orElseThrow(NotFoundRefreshTokenException::new)
          .getValue();
    }
    dto.setRefreshToken(refreshToken);
    refreshToken = tokenService.reissueAccessToken(dto.getRefreshToken(), dto.getEmployeeNo());
    return ResponseEntity.ok(new ReissueTokenDto(refreshToken));
  }
}
