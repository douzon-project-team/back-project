package com.douzon.blooming;

import lombok.Getter;

@Getter
public abstract class SearchDto {

  protected static final String DEFAULT_STRING = "";
  protected static final int DEFAULT_PAGE = 0;
  protected static final int DEFAULT_SIZE = 8;

  protected Integer pageSize;
  protected Integer page;

  protected SearchDto() {
    this.pageSize = DEFAULT_SIZE;
    this.page = DEFAULT_PAGE;
  }

  protected SearchDto(Integer pageSize, Integer page) {
    this.page = page == null || page <= 1 ? DEFAULT_PAGE : page - 1;
    this.pageSize = pageSize == null || pageSize <= 0 ? DEFAULT_SIZE : pageSize;
  }
}
