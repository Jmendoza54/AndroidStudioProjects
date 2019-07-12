package com.alain.cursos.inventario.mainModule.model.dataAccess;

import com.alain.cursos.inventario.common.pojo.Product;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);

    void onError(int resMsg);
}
