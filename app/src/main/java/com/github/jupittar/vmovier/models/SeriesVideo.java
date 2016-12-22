package com.github.jupittar.vmovier.models;

public class SeriesVideo {

  private String title;
  private String seriesid;
  private String series_postid;
  private String video_link;
  private String episode;
  private String count_comment;
  /**
   * sweibo : http://www.vmovier.com/series/45/84?debug=1&_vfrom=VmovierApp_sweibo
   * weixin : http://www.vmovier.com/series/45/84?debug=1&_vfrom=VmovierApp_weixin
   * qzone : http://www.vmovier.com/series/45/84?debug=1&_vfrom=VmovierApp_qzone
   * qq : http://www.vmovier.com/series/45/84?debug=1&_vfrom=VmovierApp_qq
   */

  private ShareLinkBean share_link;
  private String qiniu_url;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSeriesid() {
    return seriesid;
  }

  public void setSeriesid(String seriesid) {
    this.seriesid = seriesid;
  }

  public String getSeries_postid() {
    return series_postid;
  }

  public void setSeries_postid(String series_postid) {
    this.series_postid = series_postid;
  }

  public String getVideo_link() {
    return video_link;
  }

  public void setVideo_link(String video_link) {
    this.video_link = video_link;
  }

  public String getEpisode() {
    return episode;
  }

  public void setEpisode(String episode) {
    this.episode = episode;
  }

  public String getCount_comment() {
    return count_comment;
  }

  public void setCount_comment(String count_comment) {
    this.count_comment = count_comment;
  }

  public ShareLinkBean getShare_link() {
    return share_link;
  }

  public void setShare_link(ShareLinkBean share_link) {
    this.share_link = share_link;
  }

  public String getQiniu_url() {
    return qiniu_url;
  }

  public void setQiniu_url(String qiniu_url) {
    this.qiniu_url = qiniu_url;
  }

  public static class ShareLinkBean {
    private String sweibo;
    private String weixin;
    private String qzone;
    private String qq;

    public String getSweibo() {
      return sweibo;
    }

    public void setSweibo(String sweibo) {
      this.sweibo = sweibo;
    }

    public String getWeixin() {
      return weixin;
    }

    public void setWeixin(String weixin) {
      this.weixin = weixin;
    }

    public String getQzone() {
      return qzone;
    }

    public void setQzone(String qzone) {
      this.qzone = qzone;
    }

    public String getQq() {
      return qq;
    }

    public void setQq(String qq) {
      this.qq = qq;
    }
  }
}
