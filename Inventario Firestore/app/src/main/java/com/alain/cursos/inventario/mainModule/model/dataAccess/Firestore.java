package com.alain.cursos.inventario.mainModule.model.dataAccess;

import android.support.annotation.NonNull;

import com.alain.cursos.inventario.R;
import com.alain.cursos.inventario.common.BasicErrorEventCallback;
import com.alain.cursos.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.mainModule.events.MainEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class Firestore {

    private FirebaseFirestoreAPI mFirestoreAPI;
    private EventListener<QuerySnapshot> mProductsEventListener;
    private ListenerRegistration mRegistration;

    public Firestore() {
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }

    public void subscribeToProducts(final ProductsEventListener listener){
        if (mProductsEventListener == null){
            mProductsEventListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null){
                        if (e.getCode() == FirebaseFirestoreException.Code.PERMISSION_DENIED){
                            listener.onError(R.string.main_error_permission_denied);
                        } else {
                            listener.onError(R.string.main_error_server);
                        }
                    } else if (snapshots != null){
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
                }
            };
        }
        //mRegistration = mFirestoreAPI.getProductsReference().addSnapshotListener(mProductsEventListener);

        //Query query = mFirestoreAPI.getProductsReference().whereGreaterThan(Product.QUANTITY, 500);
        //Query query = mFirestoreAPI.getProductsReference().whereLessThan(Product.QUANTITY, 500);
        //Query query = mFirestoreAPI.getProductsReference().whereEqualTo(Product.QUANTITY, 500);

        //Query query = mFirestoreAPI.getProductsReference().whereGreaterThanOrEqualTo(Product.QUANTITY, 500);
        /*Query query = mFirestoreAPI.getProductsReference()
                .whereGreaterThan(Product.QUANTITY, 10)
                .whereLessThan(Product.QUANTITY, 500);*/

        //Query query = mFirestoreAPI.getProductsReference().orderBy(Product.QUANTITY, Query.Direction.ASCENDING);
        Query query = mFirestoreAPI.getProductsReference().orderBy(Product.QUANTITY,
                Query.Direction.DESCENDING)
                .limit(3);

        mRegistration = query.addSnapshotListener(mProductsEventListener);
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
        WriteBatch batch = mFirestoreAPI.getmFirestore().batch();

        batch.delete(mFirestoreAPI.getProductsReference().document(product.getId()));

        DocumentReference lastUpdatesReferences = mFirestoreAPI.getmFirestore()
                .collection("updates").document("inventario");

        Map<String, Object> updates = new HashMap<>();
        updates.put("lastUpdate", FieldValue.serverTimestamp());
        batch.set(lastUpdatesReferences, updates);

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if ( ((FirebaseFirestoreException)e).getCode() ==
                                FirebaseFirestoreException.Code.PERMISSION_DENIED){
                            callback.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                        } else {
                            callback.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                        }
                    }
                });

        /*mFirestoreAPI.getProductsReference().document(product.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if ( ((FirebaseFirestoreException)e).getCode() ==
                                FirebaseFirestoreException.Code.PERMISSION_DENIED){
                            callback.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                        } else {
                            callback.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                        }
                    }
                });*/
    }
}
