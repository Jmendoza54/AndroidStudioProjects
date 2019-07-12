package com.alain.cursos.inventario.detailModule.model;

import com.alain.cursos.inventario.common.BasicEventCallback;
import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.detailModule.events.DetailProductEvent;
import com.alain.cursos.inventario.detailModule.model.dataAccess.Firestore;

import org.greenrobot.eventbus.EventBus;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class DetailProductInteractorClass implements DetailProductInteractor {
    private Firestore mDatabase;

    public DetailProductInteractorClass() {
        mDatabase = new Firestore();
    }

    @Override
    public void updateProduct(Product product) {
        mDatabase.updateProduct(product, new BasicEventCallback() {
            @Override
            public void onSuccess() {
                post(DetailProductEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError() {
                post(DetailProductEvent.ERROR_SERVER);
            }
        });
    }

    private void post(int typeEvent) {
        DetailProductEvent event = new DetailProductEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }
}
