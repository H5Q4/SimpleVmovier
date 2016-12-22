package com.github.jupittar.vmovier.models;

public class Banner {
  /**
   * bannerid : 1000
   * title :
   * image : http://cs.vmoiver.com/Uploads/Banner/2016/06/13/575e76ed01d24.jpg
   * description :
   * addtime : 1465808622
   * extra : {"app_banner_type":"2","app_banner_param":"49274"}
   * end_time : 0
   * extra_data : {"app_banner_type":"2","app_banner_param":"49274","is_album":"0"}
   */

  private String bannerid;
  private String title;
  private String image;
  private String description;
  private String addtime;
  private String extra;
  private String end_time;
  /**
   * app_banner_type : 2
   * app_banner_param : 49274
   * is_album : 0
   */

  private ExtraData extra_data;

  //region Getters and Setters
  public String getBannerid() {
    return bannerid;
  }

  public void setBannerid(String bannerid) {
    this.bannerid = bannerid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddtime() {
    return addtime;
  }

  public void setAddtime(String addtime) {
    this.addtime = addtime;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public ExtraData getExtra_data() {
    return extra_data;
  }

  public void setExtra_data(ExtraData extra_data) {
    this.extra_data = extra_data;
  }
  //endregion

  public static class ExtraData {
    private String app_banner_type;
    private String app_banner_param;
    private String is_album;

    //region Getters and Setters
    public String getApp_banner_type() {
      return app_banner_type;
    }

    public void setApp_banner_type(String app_banner_type) {
      this.app_banner_type = app_banner_type;
    }

    public String getApp_banner_param() {
      return app_banner_param;
    }

    public void setApp_banner_param(String app_banner_param) {
      this.app_banner_param = app_banner_param;
    }

    public String getIs_album() {
      return is_album;
    }

    public void setIs_album(String is_album) {
      this.is_album = is_album;
    }
    //endregion
  }
}
