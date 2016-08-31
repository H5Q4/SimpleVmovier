package com.github.jupittar.vmovier.ui.series;


import com.github.jupittar.vmovier.data.models.Series;
import com.github.jupittar.vmovier.ui.base.Mvp;

import java.util.List;

public interface SeriesMvp {

    interface View extends Mvp.View {

        void showSeries(List<Series> series);
    }

    interface Presenter extends Mvp.Presenter<View> {

    }

}
