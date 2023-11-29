package com.douzon.blooming.main.service;

import com.douzon.blooming.main.dto.request.BarGraphDto;
import com.douzon.blooming.main.dto.request.CircleGraphDto;
import com.douzon.blooming.main.dto.request.MainPageDataDto;
import java.util.List;

public interface MainPageService {

  MainPageDataDto getMainPageData();

  List<BarGraphDto> getMainPageBarGraphData(String type);

  List<CircleGraphDto> getMainPageCircleGraphData(String type);
}
