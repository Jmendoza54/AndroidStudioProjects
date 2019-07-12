package com.jmendoza.inventario.addModule;

import com.jmendoza.inventario.addModule.events.AddProductEvent;
import com.jmendoza.inventario.common.pojo.Product;

public interface AddProductPresenter {

    void onShow();
    void onDestroy();

    void addProduct(Product product);
    void onEventListener(AddProductEvent event);
}
