package com.github.jupittar.commlib.rxbus;

public class BusEvent {

  private String tag;
  private Object object;

  public BusEvent(String tag, Object object) {

    this.tag = tag;
    this.object = object;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }
}
