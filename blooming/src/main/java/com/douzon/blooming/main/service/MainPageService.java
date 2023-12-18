package com.douzon.blooming.main.service;

import com.douzon.blooming.main.dto.request.BarGraphListDto;
import com.douzon.blooming.main.dto.request.CircleGraphListDto;
import com.douzon.blooming.main.dto.request.MainPageDataDto;

public interface MainPageService {

  MainPageDataDto getMainPageData();

  BarGraphListDto getMainPageBarGraphData(String type);

  CircleGraphListDto getMainPageCircleGraphData(String type);
}
