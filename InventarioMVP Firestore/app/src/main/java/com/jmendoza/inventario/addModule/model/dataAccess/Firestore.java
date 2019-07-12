package com.jmendoza.inventario.addModule.model.dataAccess;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jmendoza.inventario.R;
import com.jmendoza.inventario.addModule.events.AddProductEvent;
import com.jmendoza.inventario.common.BasicErrorEventCallback;
import com.jmendoza.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.jmendoza.inventario.common.pojo.Product;

public class Firestore {
    private FirebaseFirestoreAPI mFirestoreAPI;

    public Firestore(){
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }

    public void addProduct(Product product, BasicErrorEventCallback callback){
        mFirestoreAPI.getProductsReference()
                .add(product)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        callback.onSuccess();
                    }else{
                        if (task.getException() != null){
                            try {
                                throw task.getException();
                            } catch (FirebaseFirestoreException e){
                                callback.onError(AddProductEvent.ERROR_MAX_VALUE, R.string.add_product_message_validate_max_quantity);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                callback.onError(AddProductEvent.ERROR_SERVER,R.string.add_product_message_added_error);
                            }
                        }

                    }
                });
    }
}
