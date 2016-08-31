package com.github.jupittar.vmovier.ui.home;

import com.github.jupittar.commlib.custom.recyclerview.entity.SectionedItem;
import com.github.jupittar.vmovier.data.models.Movie;
import com.github.jupittar.vmovier.ui.base.Mvp;

import java.util.List;

public interface HomeMvp {

    interface View extends Mvp.View {

        int getMovieCount();

        SectionedItem<Movie> getLastMovie();

        void handleBanner(List<String> imageUrls);

        void showMovies(List<SectionedItem<Movie>> movies);

        void showLoading();

        void hideLoading();

        void showErrorLayout();

        void showErrorSnackbar(String msg);
    }

    interface Presenter extends Mvp.Presenter<View> {

    }
}
