package com.github.jupittar.vmovier.data.source;


import com.github.jupittar.vmovier.data.models.Banner;
import com.github.jupittar.vmovier.data.models.Movie;
import com.github.jupittar.vmovier.data.models.Series;
import com.github.jupittar.vmovier.data.source.remote.ExtractDataFunc;
import com.github.jupittar.vmovier.data.source.remote.ServiceGenerator;

import java.util.List;

import rx.Observable;

public class DataManager {

    private static DataManager instance = new DataManager();

    private DataManager() {

    }

    public static DataManager getInstance() {
        return instance;
    }

    public Observable<List<Series>> fetchMovieSeries(int pageIndex, int pageSize) {
        return ServiceGenerator
            .getVMovieService()
            .getSeriesList(pageIndex, pageSize)
            .map(new ExtractDataFunc<List<Series>>());
    }

    public Observable<List<Banner>> getBanner() {
        return ServiceGenerator
            .getVMovieService().getBanner()
            .map(new ExtractDataFunc<List<Banner>>());
    }

    public Observable<List<Movie>> fetLatestMovies(int pageIndex, int pageSize) {
        return ServiceGenerator
            .getVMovieService()
            .getMoviesByTab(pageIndex, pageSize, "latest")
            .map(new ExtractDataFunc<List<Movie>>());
    }
}
