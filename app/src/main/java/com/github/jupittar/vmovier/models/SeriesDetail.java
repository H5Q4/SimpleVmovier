package com.github.jupittar.vmovier.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SeriesDetail {
  /**
   * seriesid : 45
   * title : 电影自习室
   * image : http://cs.vmovier.com/Uploads/Series/2016-01-12/5694d61530e73.jpg
   * content : 【电影自习室】是V电影网出品的一档影视教学视频栏目，主要面向初级影视爱好者，为大家分享影视方面的技巧，心得，每周更新两期。
   * weekly : 每周四更新
   * count_follow : 86242
   * isfollow : 0
   * share_link : http://www.vmovier.com/series/45?_vfrom=VmovierApp
   * is_end : 0
   * update_to : 82
   * tag_name : 科普
   * post_num_per_seg : 20
   * posts : [{"from_to":"81-82","mPostList":[{"series_postid":"1781","number":"82","title":"番外篇-上海温哥华电影学院特效化妆","addtime":"2016.06.15","duration":"651","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-06-16/57617ed42f469.jpeg","source_link":"http://v.youku.com/v_show/id_XMTYwOTU2MjAzMg==.html?firsttime=0&from=y1.4-2"},{"series_postid":"1766","number":"81","title":"DIY变形宽银幕镜头效果","addtime":"2016.05.20","duration":"326","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-05-20/573ebd621476a.jpg","source_link":"http://v.youku.com/v_show/id_XMTU3NzIwNjU3Mg==.html?from=y1.7-1.2"}]},{"from_to":"1-20","mPostList":[{"series_postid":"805","number":"20","title":"绿幕抠像和光线匹配技巧","addtime":"2014.11.27","duration":"529","thumbnail":"http://cs.vmovier.com/Uploads/Series/2014-12-01/547c5be7d67ee.jpeg","source_link":"http://v.youku.com/v_show/id_XODM2MjY4NDM2.html"}]}]
   */

  private String seriesid;
  private String title;
  private String image;
  private String content;
  private String weekly;
  private String count_follow;
  private String isfollow;
  private String share_link;
  private String is_end;
  private String update_to;
  private String tag_name;
  private String post_num_per_seg;
  /**
   * from_to : 81-82
   * mPostList : [{"series_postid":"1781","number":"82","title":"番外篇-上海温哥华电影学院特效化妆","addtime":"2016.06.15","duration":"651","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-06-16/57617ed42f469.jpeg","source_link":"http://v.youku.com/v_show/id_XMTYwOTU2MjAzMg==.html?firsttime=0&from=y1.4-2"},{"series_postid":"1766","number":"81","title":"DIY变形宽银幕镜头效果","addtime":"2016.05.20","duration":"326","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-05-20/573ebd621476a.jpg","source_link":"http://v.youku.com/v_show/id_XMTU3NzIwNjU3Mg==.html?from=y1.7-1.2"}]
   */

  private List<Posts> posts;

  public String getSeriesid() {
    return seriesid;
  }

  public void setSeriesid(String seriesid) {
    this.seriesid = seriesid;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getWeekly() {
    return weekly;
  }

  public void setWeekly(String weekly) {
    this.weekly = weekly;
  }

  public String getCount_follow() {
    return count_follow;
  }

  public void setCount_follow(String count_follow) {
    this.count_follow = count_follow;
  }

  public String getIsfollow() {
    return isfollow;
  }

  public void setIsfollow(String isfollow) {
    this.isfollow = isfollow;
  }

  public String getShare_link() {
    return share_link;
  }

  public void setShare_link(String share_link) {
    this.share_link = share_link;
  }

  public String getIs_end() {
    return is_end;
  }

  public void setIs_end(String is_end) {
    this.is_end = is_end;
  }

  public String getUpdate_to() {
    return update_to;
  }

  public void setUpdate_to(String update_to) {
    this.update_to = update_to;
  }

  public String getTag_name() {
    return tag_name;
  }

  public void setTag_name(String tag_name) {
    this.tag_name = tag_name;
  }

  public String getPost_num_per_seg() {
    return post_num_per_seg;
  }

  public void setPost_num_per_seg(String post_num_per_seg) {
    this.post_num_per_seg = post_num_per_seg;
  }

  public List<Posts> getPosts() {
    return posts;
  }

  public void setPosts(List<Posts> posts) {
    this.posts = posts;
  }

  public static class Posts implements Parcelable {
    public static final Parcelable.Creator<Posts> CREATOR = new Parcelable.Creator<Posts>() {
      @Override
      public Posts createFromParcel(Parcel source) {
        return new Posts(source);
      }

      @Override
      public Posts[] newArray(int size) {
        return new Posts[size];
      }
    };
    private String from_to;
    /**
     * series_postid : 1781
     * number : 82
     * title : 番外篇-上海温哥华电影学院特效化妆
     * addtime : 2016.06.15
     * duration : 651
     * thumbnail : http://cs.vmoiver.com/Uploads/Series/2016-06-16/57617ed42f469.jpeg
     * source_link : http://v.youku.com/v_show/id_XMTYwOTU2MjAzMg==.html?firsttime=0&from=y1.4-2
     */

    private List<PostList> list;

    public Posts() {
    }

    protected Posts(Parcel in) {
      this.from_to = in.readString();
      this.list = in.createTypedArrayList(PostList.CREATOR);
    }

    public String getFrom_to() {
      return from_to;
    }

    public void setFrom_to(String from_to) {
      this.from_to = from_to;
    }

    public List<PostList> getList() {
      return list;
    }

    public void setList(List<PostList> list) {
      this.list = list;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.from_to);
      dest.writeTypedList(this.list);
    }

    public static class PostList implements Parcelable {
      public static final Parcelable.Creator<PostList> CREATOR = new Parcelable.Creator<PostList>() {
        @Override
        public PostList createFromParcel(Parcel source) {
          return new PostList(source);
        }

        @Override
        public PostList[] newArray(int size) {
          return new PostList[size];
        }
      };
      private String series_postid;
      private String number;
      private String title;
      private String addtime;
      private String duration;
      private String thumbnail;
      private String source_link;

      public PostList() {
      }

      protected PostList(Parcel in) {
        this.series_postid = in.readString();
        this.number = in.readString();
        this.title = in.readString();
        this.addtime = in.readString();
        this.duration = in.readString();
        this.thumbnail = in.readString();
        this.source_link = in.readString();
      }

      //region Getters and Setters
      public String getSeries_postid() {
        return series_postid;
      }

      public void setSeries_postid(String series_postid) {
        this.series_postid = series_postid;
      }

      public String getNumber() {
        return number;
      }

      public void setNumber(String number) {
        this.number = number;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getAddtime() {
        return addtime;
      }

      public void setAddtime(String addtime) {
        this.addtime = addtime;
      }

      public String getDuration() {
        return duration;
      }

      public void setDuration(String duration) {
        this.duration = duration;
      }

      public String getThumbnail() {
        return thumbnail;
      }
      //endregion

      public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
      }

      public String getSource_link() {
        return source_link;
      }

      public void setSource_link(String source_link) {
        this.source_link = source_link;
      }

      @Override
      public int describeContents() {
        return 0;
      }

      @Override
      public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.series_postid);
        dest.writeString(this.number);
        dest.writeString(this.title);
        dest.writeString(this.addtime);
        dest.writeString(this.duration);
        dest.writeString(this.thumbnail);
        dest.writeString(this.source_link);
      }
    }
  }
}
