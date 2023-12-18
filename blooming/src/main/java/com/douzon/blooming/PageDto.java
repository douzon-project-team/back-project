package com.douzon.blooming;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PageDto<T> {

  private List<T> list;
  private Integer currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
}
