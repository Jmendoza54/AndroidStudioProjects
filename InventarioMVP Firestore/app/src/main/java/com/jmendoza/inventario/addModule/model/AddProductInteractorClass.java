package com.jmendoza.inventario.addModule.model;

import com.jmendoza.inventario.addModule.events.AddProductEvent;
import com.jmendoza.inventario.addModule.model.dataAccess.Firestore;
import com.jmendoza.inventario.common.BasicErrorEventCallback;
import com.jmendoza.inventario.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

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
