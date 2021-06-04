package com.tuliu.translate.entity;

public class tuliuDocument {
  private Integer id;

  private String documentName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDocumentName() {
    return documentName;
  }

  public void setDocumentName(String documentName) {
    this.documentName = documentName == null ? null : documentName.trim();
  }
}
