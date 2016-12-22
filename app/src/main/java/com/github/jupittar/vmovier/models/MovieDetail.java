package com.github.jupittar.vmovier.models;

import java.util.List;

public class MovieDetail {

  /**
   * postid : 49307
   * title : VIVO玩转创意定格短片《晒照背后的生活》
   * app_fu_title :
   * intro : 如果生活的瞬间可以被一帧一帧记录下来，我们将会看到：每一次迈开的步伐里那些渺小的里程，每一次微笑定格之前的N种不同表情，每一次太阳升落在天空留下的痕迹……
   * 如果被分帧记录的生活瞬间可以串连起来走马灯似地播放，我们又将看到：由跬步积聚出的大步的坚定与勇敢，由N种表情串联起来的微笑的欢畅与淋漓，太阳漂亮的痕迹被我们的手指牵引……
   * count_comment : 6
   * is_album : 0
   * is_collect : 0
   * content : {"video":[{"image":"http://cs.vmoiver.com/Uploads/cover/2016-06-14/575f63f0d3892_cut.jpeg","title":"VIVO玩转创意定格短片《晒照背后的生活》","duration":"120","filesize":"36799526","source_link":"http://v.youku.com/v_show/id_XMTYwNjc4NzU4MA==.html","qiniu_url":"http://bsy.qiniu.vmovier.vmoiver.com/575f71537674f.mp4"}]}
   * image : http://cs.vmoiver.com/Uploads/cover/2016-06-14/575f63f0d3892_cut.jpeg
   * rating : 7.7
   * publish_time : 1465949160
   * count_like : 42
   * count_share : 277
   * cate : ["创意"]
   * share_link : {"sweibo":"http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_sweibo","weixin":"http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_weixin","qzone":"http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_qzone","qq":"http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_qq"}
   * tags :
   */

  private String postid;
  private String title;
  private String app_fu_title;
  private String intro;
  private String count_comment;
  private String is_album;
  private String is_collect;
  private Content content;
  private String image;
  private String rating;
  private String publish_time;
  private String count_like;
  private String count_share;
  /**
   * sweibo : http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_sweibo
   * weixin : http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_weixin
   * qzone : http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_qzone
   * qq : http://www.vmovier.com/49307?debug=1&_vfrom=VmovierApp_qq
   */

  private ShareLink share_link;
  private String tags;
  private List<String> cate;

  //region Getters and Setters
  public String getPostid() {
    return postid;
  }

  public void setPostid(String postid) {
    this.postid = postid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getApp_fu_title() {
    return app_fu_title;
  }

  public void setApp_fu_title(String app_fu_title) {
    this.app_fu_title = app_fu_title;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public String getCount_comment() {
    return count_comment;
  }

  public void setCount_comment(String count_comment) {
    this.count_comment = count_comment;
  }

  public String getIs_album() {
    return is_album;
  }

  public void setIs_album(String is_album) {
    this.is_album = is_album;
  }

  public String getIs_collect() {
    return is_collect;
  }

  public void setIs_collect(String is_collect) {
    this.is_collect = is_collect;
  }

  public Content getContent() {
    return content;
  }

  public void setContent(Content content) {
    this.content = content;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getPublish_time() {
    return publish_time;
  }

  public void setPublish_time(String publish_time) {
    this.publish_time = publish_time;
  }

  public String getCount_like() {
    return count_like;
  }

  public void setCount_like(String count_like) {
    this.count_like = count_like;
  }

  public String getCount_share() {
    return count_share;
  }

  public void setCount_share(String count_share) {
    this.count_share = count_share;
  }

  public ShareLink getShare_link() {
    return share_link;
  }

  public void setShare_link(ShareLink share_link) {
    this.share_link = share_link;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public List<String> getCate() {
    return cate;
  }

  public void setCate(List<String> cate) {
    this.cate = cate;
  }
  //endregion

  public static class Content {
    /**
     * image : http://cs.vmoiver.com/Uploads/cover/2016-06-14/575f63f0d3892_cut.jpeg
     * title : VIVO玩转创意定格短片《晒照背后的生活》
     * duration : 120
     * filesize : 36799526
     * source_link : http://v.youku.com/v_show/id_XMTYwNjc4NzU4MA==.html
     * qiniu_url : http://bsy.qiniu.vmovier.vmoiver.com/575f71537674f.mp4
     */

    private List<Video> video;

    public List<Video> getVideo() {
      return video;
    }

    public void setVideo(List<Video> video) {
      this.video = video;
    }

    public static class Video {
      private String image;
      private String title;
      private String duration;
      private String filesize;
      private String source_link;
      private String qiniu_url;

      //region Getters and Setters
      public String getImage() {
        return image;
      }

      public void setImage(String image) {
        this.image = image;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getDuration() {
        return duration;
      }

      public void setDuration(String duration) {
        this.duration = duration;
      }

      public String getFilesize() {
        return filesize;
      }

      public void setFilesize(String filesize) {
        this.filesize = filesize;
      }

      public String getSource_link() {
        return source_link;
      }

      public void setSource_link(String source_link) {
        this.source_link = source_link;
      }

      public String getQiniu_url() {
        return qiniu_url;
      }

      public void setQiniu_url(String qiniu_url) {
        this.qiniu_url = qiniu_url;
      }
      //endregion
    }
  }

  private static class ShareLink {
    private String sweibo;
    private String weixin;
    private String qzone;
    private String qq;

    //region Getters and Setters
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
    //endregion
  }
}
