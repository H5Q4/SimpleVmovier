package com.github.jupittar.vmovier.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
  public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
    @Override
    public Category createFromParcel(Parcel source) {
      return new Category(source);
    }

    @Override
    public Category[] newArray(int size) {
      return new Category[size];
    }
  };
  /**
   * cate_type : 0
   * orderid : 16
   * cateid : 78
   * "tab": "hot",
   * catename : 生活
   * alias : Lifeness
   * icon : http://cs.vmoiver.com/Uploads/Series/2016-04-12/570c6f4f9679c.jpg
   */

  private String cate_type;
  private String orderid;
  private String cateid;
  private String catename;
  private String alias;
  private String icon;
  private String tab;

  public Category() {
  }

  protected Category(Parcel in) {
    this.cate_type = in.readString();
    this.orderid = in.readString();
    this.cateid = in.readString();
    this.catename = in.readString();
    this.alias = in.readString();
    this.icon = in.readString();
    this.tab = in.readString();
  }

  //region Getters and Setters
  public String getCate_type() {
    return cate_type;
  }

  public void setCate_type(String cate_type) {
    this.cate_type = cate_type;
  }

  public String getOrderid() {
    return orderid;
  }

  public void setOrderid(String orderid) {
    this.orderid = orderid;
  }

  public String getCateid() {
    return cateid;
  }

  public void setCateid(String cateid) {
    this.cateid = cateid;
  }

  public String getCatename() {
    return catename;
  }

  public void setCatename(String catename) {
    this.catename = catename;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getIcon() {
    return icon;
  }
  //endregion

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getTab() {
    return tab;
  }

  public void setTab(String tab) {
    this.tab = tab;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.cate_type);
    dest.writeString(this.orderid);
    dest.writeString(this.cateid);
    dest.writeString(this.catename);
    dest.writeString(this.alias);
    dest.writeString(this.icon);
    dest.writeString(this.tab);
  }
}
