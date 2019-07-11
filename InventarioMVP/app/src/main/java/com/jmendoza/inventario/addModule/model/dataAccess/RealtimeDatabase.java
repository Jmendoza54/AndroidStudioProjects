package com.jmendoza.inventario.addModule.model.dataAccess;

import com.google.firebase.database.DatabaseError;
import com.jmendoza.inventario.R;
import com.jmendoza.inventario.addModule.events.AddProductEvent;
import com.jmendoza.inventario.common.BasicErrorEventCallback;
import com.jmendoza.inventario.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.jmendoza.inventario.common.pojo.Product;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addProduct(Product product, BasicErrorEventCallback callback){
        mDatabaseAPI.getProductsReference().push().setValue(product, (databaseError, databaseReference) -> {
            if(databaseError == null){
                callback.onSuccess();
            }else{
                switch (databaseError.getCode()){
                    case DatabaseError.PERMISSION_DENIED:
                        callback.onError(AddProductEvent.ERROR_MAX_VALUE, R.string.add_product_message_validate_max_quantity);
                        break;
                    default:
                        callback.onError(AddProductEvent.ERROR_SERVER,R.string.add_product_message_added_error);
                }
            }
        });
    }
}
