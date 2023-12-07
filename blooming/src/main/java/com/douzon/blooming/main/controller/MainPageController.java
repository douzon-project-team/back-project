package com.douzon.blooming.main.controller;

import com.douzon.blooming.main.dto.request.BarGraphListDto;
import com.douzon.blooming.main.dto.request.CircleGraphListDto;
import com.douzon.blooming.main.dto.request.MainPageDataDto;
import com.douzon.blooming.main.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {

  private final MainPageService mainPageService;

  @GetMapping
  public ResponseEntity<MainPageDataDto> getMainPageData() {
    return ResponseEntity.ok(mainPageService.getMainPageData());
  }

  @GetMapping("/bar-graph/{type}")
  public ResponseEntity<BarGraphListDto> getBarGraph(@PathVariable String type) {
    return ResponseEntity.ok(mainPageService.getMainPageBarGraphData(type));
  }

  @GetMapping("/circle-graph/{type}")
  public ResponseEntity<CircleGraphListDto> getCircleGraph(@PathVariable String type) {
    return ResponseEntity.ok(mainPageService.getMainPageCircleGraphData(type));
  }
}
