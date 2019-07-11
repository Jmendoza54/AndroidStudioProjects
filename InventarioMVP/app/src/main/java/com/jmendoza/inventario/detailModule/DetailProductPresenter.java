package com.jmendoza.inventario.detailModule;

import com.jmendoza.inventario.common.pojo.Product;
import com.jmendoza.inventario.detailModule.events.DetailProductEvent;

public interface DetailProductPresenter {

    void onCreate();
    void onDestroy();

    void updateProduct(Product product);

    void onEventListener(DetailProductEvent event);
}
