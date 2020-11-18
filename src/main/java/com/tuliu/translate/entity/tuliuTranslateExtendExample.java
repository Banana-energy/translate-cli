package com.tuliu.translate.entity;

public class tuliuTranslateExtendExample extends tuliuTranslateExample {
  private Integer offset;

  private Integer limit;

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getLimit() {
    return this.limit;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getOffset() {
    return this.offset;
  }
}
