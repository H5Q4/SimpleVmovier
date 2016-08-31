package com.github.jupittar.vmovier.data.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {


    /**
     * postid : 49307
     * title : VIVO玩转创意定格短片《晒照背后的生活》
     * pid : 1
     * app_fu_title :
     * is_xpc : 0
     * is_promote : 0
     * is_xpc_zp : 0
     * is_xpc_cp : 0
     * is_xpc_fx : 0
     * is_album : 0
     * tags :
     * recent_hot : 0
     * discussion : 0
     * image : http://cs.vmoiver.com/Uploads/cover/2016-06-14/575f63f0d3892_cut.jpeg
     * rating : 7.7
     * duration : 120
     * publish_time : 1465949160
     * like_num : 114
     * share_num : 203
     * cates : [{"cateid":"6","catename":"创意"}]
     * request_url : http://app.vmoiver.com/49307?qingapp=app_new
     */

    private String postid;
    private String title;
    private String pid;
    private String app_fu_title;
    private String is_xpc;
    private String is_promote;
    private String is_xpc_zp;
    private String is_xpc_cp;
    private String is_xpc_fx;
    private String is_album;
    private String tags;
    private String recent_hot;
    private String discussion;
    private String image;
    private String rating;
    private String duration;
    private String publish_time;
    private String like_num;
    private String share_num;
    private String request_url;
    /**
     * cateid : 6
     * catename : 创意
     */

    private List<Cates> cates;

    @Getter
    @Setter
    public static class Cates {
        private String cateid;
        private String catename;
    }
}
