package com.alain.cursos.inventario.mainModule;

import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.mainModule.events.MainEvent;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public interface MainPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);

    void onEventListener(MainEvent event);
}
