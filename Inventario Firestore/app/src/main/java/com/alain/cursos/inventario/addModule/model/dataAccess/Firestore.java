package com.alain.cursos.inventario.addModule.model.dataAccess;

import android.support.annotation.NonNull;

import com.alain.cursos.inventario.R;
import com.alain.cursos.inventario.addModule.events.AddProductEvent;
import com.alain.cursos.inventario.common.BasicErrorEventCallback;
import com.alain.cursos.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.alain.cursos.inventario.common.pojo.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class Firestore {
    private FirebaseFirestoreAPI mFirestoreAPI;

    public Firestore() {
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }

    public void addProduct(Product product, final BasicErrorEventCallback callback){
        mFirestoreAPI.getProductsReference()
                .add(product)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            callback.onSuccess();
                        } else {
                            if (task.getException() != null){
                                try {
                                    throw task.getException();
                                } catch (FirebaseFirestoreException e){
                                    callback.onError(AddProductEvent.ERROR_MAX_VALUE,
                                            R.string.addProduct_message_validate_max_quantity);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    callback.onError(AddProductEvent.ERROR_SERVER,
                                            R.string.addProduct_message_added_error);
                                }
                            }
                        }
                    }
                });
    }
}
