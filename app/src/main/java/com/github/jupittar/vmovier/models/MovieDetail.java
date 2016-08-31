package com.github.jupittar.vmovier.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Getter
    @Setter
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

        @Getter
        @Setter
        public static class Video {
            private String image;
            private String title;
            private String duration;
            private String filesize;
            private String source_link;
            private String qiniu_url;
        }
    }

    @Getter
    @Setter
    public static class ShareLink {
        private String sweibo;
        private String weixin;
        private String qzone;
        private String qq;
    }
}
