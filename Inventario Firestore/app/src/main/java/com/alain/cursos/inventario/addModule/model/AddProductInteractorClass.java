package com.alain.cursos.inventario.addModule.model;

import com.alain.cursos.inventario.addModule.events.AddProductEvent;
import com.alain.cursos.inventario.addModule.model.dataAccess.Firestore;
import com.alain.cursos.inventario.common.BasicErrorEventCallback;
import com.alain.cursos.inventario.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class AddProductInteractorClass implements AddProductInteractor {

    private Firestore mDatabase;

    public AddProductInteractorClass() {
        mDatabase = new Firestore();
    }

    @Override
    public void addProduct(Product product) {
        mDatabase.addProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(AddProductEvent.SUCCESS_ADD);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent) {
        post(typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg) {
        AddProductEvent event = new AddProductEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
