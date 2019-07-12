package com.alain.cursos.inventario.mainModule.model;

import com.alain.cursos.inventario.common.BasicErrorEventCallback;
import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.mainModule.events.MainEvent;
import com.alain.cursos.inventario.mainModule.model.dataAccess.ProductsEventListener;
import com.alain.cursos.inventario.mainModule.model.dataAccess.Firestore;

import org.greenrobot.eventbus.EventBus;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class MainInteractorClass implements MainInteractor {
    private Firestore mDatabase;

    public MainInteractorClass() {
        mDatabase = new Firestore();
    }

    @Override
    public void subscribeToProducts() {
        mDatabase.subscribeToProducts(new ProductsEventListener() {
            @Override
            public void onChildAdded(Product product) {
                post(product, MainEvent.SUCCESS_ADD);
            }

            @Override
            public void onChildUpdated(Product product) {
                post(product, MainEvent.SUCCESS_UPDATE);
            }

            @Override
            public void onChildRemoved(Product product) {
                post(product, MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int resMsg) {
                post(MainEvent.ERROR_SERVER, resMsg);
            }
        });
    }

    @Override
    public void unsubscribeToProducts() {
        mDatabase.unsubscribeToProducts();
    }

    @Override
    public void removeProduct(Product product) {
        mDatabase.removeProduct(product, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent){
        post(null, typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg){
        post(null, typeEvent, resMsg);
    }

    private void post(Product product, int typeEvent){
        post(product, typeEvent, 0);
    }

    private void post(Product product, int typeEvent, int resMsg) {
        MainEvent event = new MainEvent();
        event.setProduct(product);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
