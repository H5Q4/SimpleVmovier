package com.github.jupittar.vmovier.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

        @Getter
        @Setter
        public static class ShareLinkBean {
            private String sweibo;
            private String weixin;
            private String qzone;
            private String qq;
        }
}
