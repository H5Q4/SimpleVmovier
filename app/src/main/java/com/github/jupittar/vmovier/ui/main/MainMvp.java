package com.github.jupittar.vmovier.ui.main;

import com.github.jupittar.vmovier.ui.base.Mvp;

public interface MainMvp {

    interface View extends Mvp.View {

    }

    interface Presenter extends Mvp.Presenter<View> {

    }
}
