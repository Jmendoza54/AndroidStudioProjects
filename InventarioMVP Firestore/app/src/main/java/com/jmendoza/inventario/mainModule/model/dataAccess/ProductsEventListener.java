package com.jmendoza.inventario.mainModule.model.dataAccess;

import com.jmendoza.inventario.common.pojo.Product;

public interface ProductsEventListener {
    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);

    void onError(int resMsg);
}
