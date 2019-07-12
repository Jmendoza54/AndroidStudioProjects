package com.jmendoza.inventario.detailModule.model;

import com.jmendoza.inventario.common.BasicEventCallback;
import com.jmendoza.inventario.common.pojo.Product;
import com.jmendoza.inventario.detailModule.events.DetailProductEvent;
import com.jmendoza.inventario.detailModule.model.dataAccess.Firestore;

import org.greenrobot.eventbus.EventBus;

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
