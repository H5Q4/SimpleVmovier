package com.github.jupittar.vmovier.network.entities;

public class RawResponse<T> {

  private String status;
  private String msg;
  private T data;

  //region Getters and Setters
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
  //endregion
}
