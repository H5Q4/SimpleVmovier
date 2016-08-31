package com.github.jupittar.vmovier.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category implements Parcelable {
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
}
