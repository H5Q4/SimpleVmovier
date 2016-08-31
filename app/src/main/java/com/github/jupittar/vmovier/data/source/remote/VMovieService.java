package com.github.jupittar.vmovier.data.source.remote;

import com.github.jupittar.vmovier.data.models.BackstageCategory;
import com.github.jupittar.vmovier.data.models.Banner;
import com.github.jupittar.vmovier.data.models.Category;
import com.github.jupittar.vmovier.data.models.Movie;
import com.github.jupittar.vmovier.data.models.MovieDetail;
import com.github.jupittar.vmovier.data.models.Series;
import com.github.jupittar.vmovier.data.models.SeriesDetail;
import com.github.jupittar.vmovier.data.models.SeriesVideo;
import com.github.jupittar.vmovier.data.source.remote.entities.RawResponse;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface VMovieService {

    @FormUrlEncoded
    @POST("/apiv3/backstage/getPostByCate")
    Observable<RawResponse<List<Movie>>> getBackstageList(
            @Field("p") int pageIndex,
            @Field("size") int size,
            @Field("cateid") String cateId
    );

    @POST("/apiv3/backstage/getCate")
    Observable<RawResponse<List<BackstageCategory>>> getBackstageCategories();

    @FormUrlEncoded
    @POST("/apiv3/series/getVideo")
    Observable<RawResponse<SeriesVideo>> getSeriesVideoById(
      @Field("series_postid") String postId
    );

    @FormUrlEncoded
    @POST("/apiv3/series/view")
    Observable<RawResponse<SeriesDetail>> getSeriesDetail(
        @Field("seriesid") String SeriesId
    );

    @FormUrlEncoded
    @POST("/apiv3/series/getList")
    Observable<RawResponse<List<Series>>> getSeriesList(
        @Field("p") int pageIndex,
        @Field("size") int size
    );

    @FormUrlEncoded
    @POST("/apiv3/post/getPostInCate")
    Observable<RawResponse<List<Movie>>> getMoviesInCate(
        @Field("p") int pageIndex,
        @Field("size") int size,
        @Field("cateid") String cateId
    );

    @POST("/apiv3/cate/getList")
    Observable<RawResponse<List<Category>>> getCateList();

    @POST("/apiv3/index/getBanner")
    Observable<RawResponse<List<Banner>>> getBanner();

    @FormUrlEncoded
    @POST("/apiv3/post/view")
    Observable<RawResponse<MovieDetail>> getMovieDetail(
        @Field("postid") String postId
    );

    @FormUrlEncoded
    @POST("/apiv3/post/getPostByTab")
    Observable<RawResponse<List<Movie>>> getMoviesByTab(
        @Field("p") int pageIndex,
        @Field("size") int size,
        @Field("tab") String tab
    );

}
