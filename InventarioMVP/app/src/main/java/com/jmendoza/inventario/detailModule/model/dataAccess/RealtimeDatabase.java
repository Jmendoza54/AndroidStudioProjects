package com.jmendoza.inventario.detailModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jmendoza.inventario.common.BasicEventCallback;
import com.jmendoza.inventario.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.jmendoza.inventario.common.pojo.Product;

public class RealtimeDatabase {

    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void updateProduct(Product product, BasicEventCallback callback){
        mDatabaseAPI.getProductsReference().child(product.getId()).setValue(product)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError());
    }
}
