package com.github.jupittar.vmovier.data.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Getter
    @Setter
    public static class ExtraData {
        private String app_banner_type;
        private String app_banner_param;
        private String is_album;
    }
}
