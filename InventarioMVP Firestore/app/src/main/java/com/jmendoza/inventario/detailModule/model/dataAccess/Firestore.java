package com.jmendoza.inventario.detailModule.model.dataAccess;

import com.jmendoza.inventario.common.BasicEventCallback;
import com.jmendoza.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.jmendoza.inventario.common.pojo.Product;

public class Firestore {

    private FirebaseFirestoreAPI mFirestoreAPI;

    public Firestore() {
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }

    public void updateProduct(Product product, BasicEventCallback callback){
        mFirestoreAPI.getProductsReference()
                .document(product.getId())
                .set(product)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError());
    }
}
