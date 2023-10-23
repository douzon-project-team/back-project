package com.douzon.blooming.productlog.item;

import lombok.Getter;

public enum ProductLogType {
  INSERT("입력"), UPDATE("수정"), DELETE("삭제");

  @Getter
  private final String korean;

  ProductLogType(String korean) {
    this.korean = korean;
  }
}
