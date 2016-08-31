package com.github.jupittar.vmovier.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Getter
    @Setter
    public static class Posts implements Parcelable {
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

        @Getter
        @Setter
        public static class PostList implements Parcelable {
            private String series_postid;
            private String number;
            private String title;
            private String addtime;
            private String duration;
            private String thumbnail;
            private String source_link;

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

        public Posts() {
        }

        protected Posts(Parcel in) {
            this.from_to = in.readString();
            this.list = in.createTypedArrayList(PostList.CREATOR);
        }

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
    }
}
