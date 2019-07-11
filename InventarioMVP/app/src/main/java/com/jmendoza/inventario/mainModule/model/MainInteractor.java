package com.jmendoza.inventario.mainModule.model;

import com.jmendoza.inventario.common.pojo.Product;

public interface MainInteractor {
    void subscribeToProducts();
    void unsubscribeToProducts();

    void removeProduct(Product product);
}
