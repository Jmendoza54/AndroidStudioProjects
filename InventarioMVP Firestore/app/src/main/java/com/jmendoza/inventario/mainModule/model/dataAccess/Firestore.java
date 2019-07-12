package com.jmendoza.inventario.mainModule.model.dataAccess;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.jmendoza.inventario.R;
import com.jmendoza.inventario.common.BasicErrorEventCallback;
import com.jmendoza.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.jmendoza.inventario.common.pojo.Product;
import com.jmendoza.inventario.mainModule.events.MainEvent;


public class Firestore {

    private FirebaseFirestoreAPI mFirestoreAPI;
    private EventListener<QuerySnapshot> mProductsEventListener;
    private ListenerRegistration mRegistration;

    public Firestore() {
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }


    public void subscribeToProducts(final ProductsEventListener listener){

        if (mProductsEventListener == null){
            mProductsEventListener = (snapshots, e) -> {
                if(e != null){
                    if (e.getCode() == FirebaseFirestoreException.Code.PERMISSION_DENIED){
                        listener.onError(R.string.main_error_permission_denied);
                    }else {
                        listener.onError(R.string.main_error_server);
                    }
                }else if (snapshots != null){
                    for (DocumentChange dc : snapshots.getDocumentChanges()){
                        switch (dc.getType()){
                            case ADDED:
                                listener.onChildAdded(getProduct(dc));
                                break;
                            case MODIFIED:
                                listener.onChildUpdated(getProduct(dc));
                                break;
                            case REMOVED:
                                listener.onChildRemoved(getProduct(dc));
                                break;
                        }
                    }
                }
            };

        }
        mRegistration = mFirestoreAPI.getProductsReference().addSnapshotListener(mProductsEventListener);
    }

    private Product getProduct(DocumentChange documentChange) {
        Product product = documentChange.getDocument().toObject(Product.class);
        product.setId(documentChange.getDocument().getId());

        return product;
    }

    public void unsubscribeToProducts(){
        if (mRegistration != null){
            mRegistration.remove();
        }
    }

    public void removeProduct(Product product, final BasicErrorEventCallback callback){
        mFirestoreAPI.getProductsReference()
                .document(product.getId())
                .delete()
                .addOnSuccessListener(aVoid -> callback.onSuccess()).addOnFailureListener(e -> {
                    if ( ((FirebaseFirestoreException)e).getCode() == FirebaseFirestoreException.Code.PERMISSION_DENIED ){
                        callback.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                    }else {
                        callback.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                    }
                });

    }
}
