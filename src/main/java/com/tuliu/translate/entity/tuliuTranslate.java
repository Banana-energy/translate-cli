package com.tuliu.translate.entity;

public class tuliuTranslate {
    private Integer id;

    private String name;

    private String hashKey;

    private String zh;

    private String en;

    private String ru;

    private String addzh;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey == null ? null : hashKey.trim();
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en == null ? null : en.trim();
    }

  public String getRu() {
    return ru;
  }

  public void setRu(String ru) {
    this.ru = ru == null ? null : ru.trim();
  }

  public String getAddzh() {
    return addzh;
  }

  public void setAddzh(String addzh) {
    this.addzh = addzh == null ? null : addzh.trim();
  }
}
